<template>
    <div>
        <h2>Delete a project and all its data</h2>
        <form v-on:submit.prevent="deleteForm">
            <label>
                Project name: <input type="text" v-model="form.name"/>
            </label>
            <button>Submit</button>
        </form>
    </div>
</template>

<script>
export default {
    name: 'DeleteProject',
    data() {
        return {
            form: {
                name: ""
            }
        }
    },
    methods: {
        deleteForm() {
            this.axios
              .delete(this.$backend.getUrlDeleteProject(this.form.name))
              .catch(function (error) {
                if (error.response) {
                    window.alert(error.response.data);
                }
              })
              .then(() => {
                this.$emit('project-deleted')
              })
        }
    }
}
</script>