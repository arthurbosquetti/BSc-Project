<template>
    <div>
        <b-modal
        id="modal-prevent-closing"
        ref="modal"
        @ok="handleVerification"
        @cancel="exitPage"
        centered
        hide-header-close
        no-close-on-backdrop
        no-close-on-esc
        >
            <template #modal-header>
                <b-container fluid>
                    <b-row>
                        <b-col cols="10"><h5>Enter Manager Credentials</h5></b-col>
                        <b-col style="text-align:right"><b-icon icon="person-bounding-box" class="ml-2" font-scale="1.5"></b-icon></b-col>
                    </b-row>
                </b-container>       
            </template>

            <form ref="form" @submit.stop.prevent="handleSubmit">
                <b-form-group
                label="Password"
                label-for="name-input"
                invalid-feedback="Password is required"
                >
                    <b-input-group>
                        <b-input-group-prepend is-text>
                            <b-icon icon="shield-lock" variant="dark"></b-icon>
                        </b-input-group-prepend>
                        <b-form-input
                        id="name-input"
                        v-model="password"
                        type="password"
                        required
                        ></b-form-input>
                    </b-input-group>
                </b-form-group>
            </form>
            <template #modal-footer="{ ok, cancel }">
                <b-button variant="outline-danger" @click="cancel()">Return to All Projects</b-button>
                <b-button variant="outline-success" @click="ok()">Verify Credentials</b-button>
            </template>
        </b-modal>
    </div>
  </template>

<script>
import router from '@/router'
import { sha256 } from 'js-sha256';

export default {
    name: 'ManagerVerification',
    data() {
      return {
        password: '',
      }
    },
    mounted() {
        this.$refs['modal'].show()
    },
    methods: {
      checkFormValidity() {
        return this.$refs.form.checkValidity()
      },
      async handleVerification(bvModalEvent) {
        // Prevent modal from closing
        bvModalEvent.preventDefault()
        // Trigger submit handler
        await this.handleSubmit()
      },
      async handleSubmit() {
        // Exit when the form isn't valid
        if (!this.checkFormValidity()) {
          return
        }
        const hash = sha256(this.password);
        let verificationRequest = { "hash": hash}
        this.$axios.post(this.$backend.getUrlVerifyCredentials(), verificationRequest)
                .then(() => {
                this.$bvModal.hide('modal-prevent-closing')
            })
                .catch(error => {
                if (error.response) {
                    window.alert(error.response.data.message);
                }
            });

      },
      exitPage(bvModalEvent) {
        // Prevent modal from closing
        bvModalEvent.preventDefault()
        router.push({ name: 'ListProject' }).catch(() => {})
      }
    }
  }
</script>