<template>
    <div>
        Here goes the FFT Complete Graph...
    </div>

</template>

<script>
export default {
    name: 'GraphFFTComplete',
    data() {
        return {
            projectName: '',
            testDataEntries: []
        }
    },
    async mounted() {
        this.projectName = this.$route.params.projectName
        await this.fetch(this.projectName)
    },
    methods: {
        async fetch(projectName) {
            await this.$axios.get(this.$backend.getUrlProjectTestDataList(projectName))
            .then(res => {
                this.testDataEntries = res.data
              })
        },
    }
}
</script>

