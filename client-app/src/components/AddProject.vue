<template>
    <div>
        <h2>Track new project</h2>
        <form v-on:submit.prevent="submitForm">
            <label>
                Project name: <input type="text" v-model="form.name"/>
            </label>
            <button>Submit</button>
        </form>
    </div>
</template>

<script>
export default {
    name: 'AddProject',
    data() {
        return {
            form: {
                name: ""
            }
        }
    },
    methods: {
        submitForm() {
            this.axios.post(this.$backend.getUrlPostProject(), this.form)
              .then(() => {
                console.log(this.form.name + " added!")
                this.form.name = ""
                this.$emit('new-project')
              })
        }
    }
}
</script>