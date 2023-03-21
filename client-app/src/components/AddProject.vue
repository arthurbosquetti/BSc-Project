<template>
    <div>
        <h2>Track a new project</h2>
        <form v-on:submit.prevent="submitForm">
            <label>Project name: <input type="text" v-model="form.name" size="30" required placeholder="A descriptive project name"/></label><br>
            <label>Nittany Report Url: <input type="url" v-model="form.nittanyUrl" size="60" required placeholder="The source Nittany Report page"></label><br>
            <label>FFT (Full Functional Testing) Deadline: <input type="date" min="2023-01-01" v-model="form.fftDeadline"></label><br>
            <button>Submit</button><br>
        </form>
        <p id="form-submitted"></p>
    </div>
</template>

<script>
export default {
    name: 'AddProject',
    data() {
        return {
            form: {
                name: "",
                nittanyUrl: "",
                fftDeadline: ""
            }
        }
    },
    methods: {
        submitForm() {
            this.displayLoadingMessage()
            this.axios.post(this.$backend.getUrlPostProject(), this.form)
              .catch(function (error) {
                if (error.response) {
                    window.alert(error.response.data);
                }
              })
              .then(() => {
                this.form.name = ""
                this.form.nittanyUrl = ""
                this.fftDeadline = new Date()
                this.$emit('new-project')
              })
        },
        displayLoadingMessage() {
            document.getElementById('form-submitted').innerText="Validating Project name and Nittany Report URL... this might take a minute";
        }
    }
}
</script>