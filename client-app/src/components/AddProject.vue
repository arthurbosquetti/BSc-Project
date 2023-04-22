<template>
    <div class="marginated">
        <b-form @submit="onSubmit" @reset="onReset" v-if="show">
            <b-form-group
                id="input-group-project-name"
                label="Project Name"
                label-for="input-project-name"
                description="A descriptive project name, e.g. MXA902 5.0.X FFT. Can be changed later."
            >
                <b-input-group size="md" class="mb-2">
                    <b-input-group-prepend is-text>
                        <b-icon icon="bookmark" variant="dark"></b-icon>
                    </b-input-group-prepend>
                    <b-form-input
                    id="input-project-name"
                    v-model="form.name"
                    type="text"
                    placeholder="Enter project name"
                    required
                    ></b-form-input>
                </b-input-group>
                
            </b-form-group>

            <b-form-group
                id="input-group-nittany-url"
                label="Nittany Report URL"
                label-for="input-nittany-url"
                description="The URL to this project's Nittany Report page. Cannot be changed later."
            >
                <b-input-group class="mb-2">
                    <b-input-group-prepend is-text variant="secondary">
                        <b-icon icon="link45deg" variant="dark"></b-icon>
                    </b-input-group-prepend>
                    <b-form-input
                    id="input-nittany-url"
                    v-model="form.nittanyUrl"
                    type="url"
                    placeholder="Enter URL"
                    required
                    ></b-form-input>
                </b-input-group>    
            </b-form-group>

            <b-form-group
            id="input-group-fft-deadline"
            label="FFT Deadline"
            label-for="input-fft-deadline"
            description="Optional. Can be changed later."
            >
                <b-form-datepicker
                    button-variant="secondary"
                    :min="minDate"
                    close-button
                    close-button-variant="warning"
                    reset-button
                    reset-button-variant="danger"
                    nav-button-variant="dark"
                    offset="-2"
                    no-highlight-today
                    placeholder="Select a date"
                    v-model="form.fftDeadline"
                    id="input-fft-deadline"
                    :date-format-options="{ year: 'numeric', month: 'short', day: '2-digit', weekday: 'short' }"
                    locale="en"
                ></b-form-datepicker>
            </b-form-group>

            <b-form-group
            id="input-group-components-list"
            label="Components"
            label-for="input-components-list"
            description="Use the tag 'all' to include all components. Case insensitive. Can be changed later."
            >
                <b-input-group class="mb-2">
                    <b-input-group-prepend is-text variant="secondary">
                        <b-icon icon="tags" variant="dark"></b-icon>
                    </b-input-group-prepend>
                    <b-form-tags
                    remove-on-delete
                    input-id="components-list"
                    v-model="form.componentsList"
                    placeholder="Add a component"
                    ></b-form-tags>
                </b-input-group>
            </b-form-group>
        
            <b-card header-tag="header" bg-variant="light">
                <template #header>
                    <p class="mb-0" style="font-weight: bold">Project Object (JSON)</p>
                </template>
                <b-card-text><pre class="m-0">{{ form }}</pre></b-card-text>
            </b-card>

            <b-button type="submit" variant="primary" class="mr-2 mt-2" :disabled="!buttonAccessible.submitButton">Submit</b-button>
            <b-button type="reset" variant="danger" class="mr-2 mt-2" :disabled="!buttonAccessible.resetButton">Reset</b-button>
            <b-button variant="info" disabled class="mt-2" v-if="buttonAccessible.loadingButton">
                <b-spinner small></b-spinner>
                Verifying the project name and the Nittany Report URL...
            </b-button>
        </b-form>
        <p class="bottom"></p>
    </div>
</template>

<script>
export default {
    name: 'AddProject',
    data() {
        return {
            form: {
                name: '',
                nittanyUrl: '',
                fftDeadline: '',
                componentsList: ['all']
            },
            show: true,
            buttonAccessible: {
                submitButton: true,
                resetButton: true,
                loadingButton: false
            },
            minDate: new Date()
        }
    },
    methods: {
        onSubmit(event) {
            event.preventDefault()
            this.buttonAccessible.submitButton = false
            this.buttonAccessible.loadingButton = true
            this.buttonAccessible.resetButton = false
            this.$axios.post(this.$backend.getUrlPostProject(), this.form)
            .then(() => {
                this.$emit('new-project')
            })
            .catch(error => {
                if (error.response) {
                    this.buttonAccessible.submitButton = true
                    this.buttonAccessible.resetButton = true
                    this.buttonAccessible.loadingButton = false
                    window.alert(error.response.data);
                } 
              })
        },
        onReset(event) {
            event.preventDefault()
            this.form.name = ''
            this.form.nittanyUrl = ''
            this.form.fftDeadline = ''
            this.form.componentsList = ['all']
            // Trick to reset/clear native browser form validation state
            this.show = false
            this.$nextTick(() => {
            this.show = true
            })
        }
      }
}
</script>

<style>

label {
    font-weight: bold;
}

</style>