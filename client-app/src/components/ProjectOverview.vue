<template>
    <div>
        Here you can find all relevant information about {{ this.projectName }}.
    </div>
</template>

<script>
export default {
    data() {
        return {
            projectName: ''
        }
    },
    mounted() {
        this.projectName = this.$route.params.projectName

    }

}
</script>