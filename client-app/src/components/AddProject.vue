<template>
    <div class="marginated">
        <ManagerVerification v-if="true"/>
        <b-form @submit="onSubmit" @reset="onReset" v-if="show">
            <b-container fluid>
                <b-row>
                    <b-col>
                        <b-form-group
                            id="input-group-project-title"
                            label="Shure Project Title"
                            label-for="input-project-title"
                            description="Project title, e.g. Hermes II. Cannot be changed later."
                        >
                            <b-input-group size="md" class="mb-2">
                                <b-input-group-prepend is-text>
                                    <b-icon icon="type" variant="dark"></b-icon>
                                </b-input-group-prepend>
                                <b-form-input
                                id="input-project-title"
                                v-model="projectTitle"
                                type="text"
                                placeholder="Enter project title"
                                required
                                :disabled="submitted"
                                ></b-form-input>
                            </b-input-group>
                        </b-form-group>
                    </b-col>
                    <b-col>
                        <b-form-group
                            id="input-group-device-name"
                            label="Device Name"
                            label-for="input-device-name"
                            description="Device name, e.g. MXA920-S. Cannot be changed later."
                        >
                            <b-input-group size="md" class="mb-2">
                                <b-input-group-prepend is-text>
                                    <b-icon icon="mic" variant="dark"></b-icon>
                                </b-input-group-prepend>
                                <b-form-input
                                id="input-device-name"
                                v-model="deviceName"
                                type="text"
                                placeholder="Enter device name"
                                required
                                :disabled="submitted"
                                ></b-form-input>
                            </b-input-group>
                        </b-form-group>
                    </b-col>
                    <b-col>
                        <b-form-group
                        id="input-group-release-name"
                        label="Release Name"
                        label-for="input-release-name"
                        description="Release name, e.g. 5.0.X. Cannot be changed later."
                        >
                            <b-input-group size="md" class="mb-2">
                                <b-input-group-prepend is-text>
                                    <b-icon icon="cursor" variant="dark"></b-icon>
                                </b-input-group-prepend>
                                <b-form-input
                                id="input-release-name"
                                v-model="releaseName"
                                type="text"
                                placeholder="Enter release name"
                                required
                                :disabled="submitted"
                                ></b-form-input>
                            </b-input-group>
                        </b-form-group>
                    </b-col>
                </b-row>
            </b-container>
            
            <b-form-group
            id="input-group-name"
            label="Project Name"
            label-for="input-name"
            description="The identifier for this project in this application. Cannot be changed later."
            >
                <b-input-group size="md" class="mb-2">
                    <b-input-group-prepend is-text>
                        <b-icon icon="bookmark" variant="dark"></b-icon>
                    </b-input-group-prepend>
                    <b-form-input
                    id="input-name"
                    v-model="form.name"
                    type="text"
                    placeholder="Enter the fields above"
                    disabled
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
                    :disabled="submitted"
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
                    dark
                    nav-button-variant="light"
                    :min="minDate"
                    close-button
                    close-button-variant="light"
                    reset-button
                    reset-button-variant="warning"
                    offset="-2"
                    no-highlight-today
                    placeholder="Select a date"
                    v-model="form.fftDeadline"
                    id="input-fft-deadline"
                    :date-format-options="{ year: 'numeric', month: 'short', day: '2-digit', weekday: 'short' }"
                    locale="en"
                    :disabled="submitted"
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
                    :disabled="submitted"
                    ></b-form-tags>
                </b-input-group>
            </b-form-group>
        
            <b-card header-tag="header" bg-variant="light">
                <template #header>
                    <p class="mb-0" style="font-weight: bold">Project Object (JSON)</p>
                </template>
                <b-card-text><pre class="m-0">{{ form }}</pre></b-card-text>
            </b-card>

            <b-button type="submit"  variant="primary" class="mr-2 mt-2" v-if="!submitted">Submit<b-icon icon="check2" class="ml-2"></b-icon></b-button>
            <b-button type="reset"  variant="warning" class="mr-2 mt-2" v-if="!submitted">Reset<b-icon icon="arrow-clockwise" class="ml-2"></b-icon></b-button>
            <b-button variant="info" disabled class="mt-2" v-if="submitted">
                <b-spinner small></b-spinner>
                Verifying the project name and the Nittany Report URL...
            </b-button>
        </b-form>
        <p class="bottom"></p>
    </div>
</template>

<script>
import ManagerVerification from './ManagerVerification.vue'

export default {
    name: "AddProject",
    data() {
        return {
            form: {
                name: "",
                nittanyUrl: "",
                fftDeadline: "",
                releaseName: "",
                componentsList: ["all"]
            },
            show: true,
            submitted: false,
            minDate: new Date(),
            projectTitle: "",
            deviceName: "",
            releaseName: ""
        };
    },
    methods: {
        onSubmit(event) {
            event.preventDefault();
            this.submitted = true;
            this.$axios.post(this.$backend.getUrlPostProject(), this.form)
                .then(() => {
                this.submitted = false
                this.resetFields()
                window.alert("Success!");
            })
                .catch(error => {
                if (error.response) {
                    this.submitted = false;
                    window.alert(error.response.data);
                }
            });
        },
        onReset(event) {
            event.preventDefault();
            this.form.name = "";
            this.form.nittanyUrl = "";
            this.form.fftDeadline = "";
            this.form.componentsList = ["all"];
            this.projectTitle = "";
            this.deviceName = "";
            this.releaseName = "";
            // Trick to reset/clear native browser form validation state
            this.show = false;
            this.$nextTick(() => {
                this.show = true;
            });
        },
        resetFields() {
            this.form.name = "";
            this.form.nittanyUrl = "";
            this.form.fftDeadline = "";
            this.form.componentsList = ["all"];
            this.projectTitle = "";
            this.deviceName = "";
            this.releaseName = "";
            // Trick to reset/clear native browser form validation state
            this.show = false;
            this.$nextTick(() => {
                this.show = true;
            });
        }
    },
    watch: {
        projectTitle: function () {
            this.form.name = (this.projectTitle + " " + this.deviceName + " " + this.releaseName).trim();
        },
        deviceName: function () {
            this.form.name = (this.projectTitle + " " + this.deviceName + " " + this.releaseName).trim();
        },
        releaseName: function () {
            this.form.releaseName = this.releaseName.trim();
            this.form.name = (this.projectTitle + " " + this.deviceName + " " + this.releaseName).trim();
        }
    },
    components: { ManagerVerification }
}
</script>

<style>

label {
    font-weight: bold;
}

</style>