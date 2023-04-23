<template>
    <div>
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
                v-model="projectName"
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
                v-model="project['nittanyUrl']"
                type="url"
                disabled
                ></b-form-input>
            </b-input-group>    
        </b-form-group>
        And more to come...
    </div>
</template>

<script>
export default {
    data() {
        return {
            projectName: '',
            project: {}
        }
    },
    methods: {
        async fetch(projectName) {
            await this.$axios
              .get(this.$backend.getUrlGetProject(projectName))
              .then(res => {
                this.project = res.data
              })
        }
    },
    async mounted() {
        this.projectName = this.$route.params.projectName
        await this.fetch(this.projectName)
    }

}
</script>