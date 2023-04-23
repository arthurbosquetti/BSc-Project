<template>
    <div>
        <b-form @submit="onSubmit">
            <b-form-group
                id="input-group-project-name"
                label="Project Name"
                label-for="input-project-name"
            >
                <b-input-group size="md" class="mb-2">
                    <b-input-group-prepend is-text>
                        <b-icon icon="bookmark" variant="dark"></b-icon>
                    </b-input-group-prepend>
                    <b-form-input
                    id="input-project-name"
                    v-model="form.name"
                    type="text"
                    disabled
                    ></b-form-input>
                </b-input-group>
                
            </b-form-group>

            <b-form-group
                id="input-group-nittany-url"
                label="Nittany Report URL"
                label-for="input-nittany-url"
                description="The URL to this project's Nittany Report page."
            >
                <b-input-group class="mb-2">
                    <b-input-group-prepend is-text variant="secondary">
                        <b-icon icon="link45deg" variant="dark"></b-icon>
                    </b-input-group-prepend>
                    <b-form-input
                    id="input-nittany-url"
                    v-model="form.nittanyUrl"
                    type="url"
                    disabled
                    ></b-form-input>
                </b-input-group>    
            </b-form-group>

            <b-form-group
            id="input-group-fft-deadline"
            label="FFT Deadline"
            label-for="input-fft-deadline"
            description="You can change the FFT deadline."
            >
                <b-form-datepicker
                    button-variant="secondary"
                    dark
                    nav-button-variant="light"
                    close-button
                    close-button-variant="warning"
                    reset-button
                    reset-button-variant="danger"
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
            description="Changing your components list will only affect future bug counts. Use the tag 'all' to include all components. Case insensitive."
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

            <b-button type="submit" variant="primary" class="mr-2 mt-2">Submit<b-icon icon="check2" class="ml-2"></b-icon></b-button>
        </b-form>

        <b-button v-b-toggle="'collapse-2'" class="mt-2" variant="danger" block>Danger Zone<b-icon icon="exclamation-triangle-fill" class="ml-2"></b-icon></b-button>
        <b-collapse id="collapse-2">
            <b-card>
                <b-card-text style="font-weight: bold;">Are you sure you want to delete all data for {{ projectName }}? This action cannot be undone!</b-card-text>
                <b-form>
                <b-input-group>
                    <b-form-input
                    v-model="deleteString"
                    type="text"
                    :state="validation"
                    required>
                    </b-form-input>
                    <b-input-group-append>
                        <b-button v-b-modal.modal-1 variant="danger" @click="deleteProject">Delete project<b-icon icon="trash" class="ml-2"></b-icon></b-button>
                    </b-input-group-append>
                    <b-form-invalid-feedback :state="validation">
                        Input must match the project name.
                    </b-form-invalid-feedback>
                    <b-form-valid-feedback :state="validation">
                        Ready to delete.
                    </b-form-valid-feedback>
                </b-input-group>
            </b-form>
            </b-card>
        </b-collapse>

        <p class="bottom"></p>
    </div>
</template>

<script>
import router from '@/router'

export default {
    data() {
        return {
            projectName: '',
            form: {
                name: '',
                nittanyUrl: '',
                fftDeadline: null,
                componentsList: [],
            },
            deleteString: ''
        }
    },
    methods: {
        async fetch(projectName) {
            await this.$axios
              .get(this.$backend.getUrlGetProject(projectName))
              .then(res => {
                this.form.name = res.data.name
                this.form.nittanyUrl = res.data.nittanyUrl
                this.form.fftDeadline = res.data.fftDeadline
                this.form.componentsList = res.data.componentsList
              })
        },
        onSubmit(event) {
            event.preventDefault()

            // This is necessary to avoid verifying the URL of the updated project
            let submissionForm = Object.assign({}, this.form)
            delete submissionForm.nittanyUrl

            this.$axios.patch(this.$backend.getUrlPatchProject(), submissionForm)
            .then(() => {
                this.$emit('patch-project')
            })
            .catch(error => {
                if (error.response) {
                    window.alert(error.response.data);
                } 
              })
        },
        deleteProject() {
            if (!this.validation) {
                this.deleteString = ''
                return
            }
            this.$axios
              .delete(this.$backend.getUrlDeleteProject(this.projectName))
              .catch(function (error) {
                if (error.response) {
                    window.alert(error.response.data);
                }
              })
              .then(() => {
                router.push({ name: 'ListProject' }).catch(() => {})
              })
        }
    },
    computed: {
      validation() {
        return this.deleteString == this.projectName
      }
    },
    async mounted() {
        this.projectName = this.$route.params.projectName
        await this.fetch(this.projectName)

    }

}
</script>