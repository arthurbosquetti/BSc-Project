<template>
    <div class="marginated">
        Here you can find everything about {{ projectName }}!
    </div>
</template>

<script>
export default {
    name: 'ProjectPage',
    data() {
        return {
            projectName: '',
        }
    },
    async mounted() {
        this.projectName = this.$route.params.projectName
    }
}


</script>