<template>
    <div>
        <h2>List of projects currently tracked</h2>
        <ul>
            <li
             v-bind:key="p.name"
             v-for="p in sortProjects">
             {{ p.name }} -
             <router-link :to="{ name: 'GraphsTestDataEntries', params: { projectName: p.name }}">Test data entries (Graphs)</router-link> -
             <router-link :to="{ name: 'GraphsBugDataEntries', params: { projectName: p.name }}">Bug data entries (Graphs)</router-link> -
             <router-link :to="{ name: 'ListTestDataEntries', params: { projectName: p.name }}">Test data entries (Raw)</router-link> -
             <router-link :to="{ name: 'ListBugDataEntries', params: { projectName: p.name }}">Bug data entries (Raw)</router-link>
            </li>
        </ul>
        <p>One easy way of displaying project status:</p>
        <b-badge variant="primary">Primary</b-badge>
        <b-badge variant="secondary">Secondary</b-badge>
        <b-badge variant="success">Success</b-badge>
        <b-badge variant="danger">Danger</b-badge>
        <b-badge variant="warning">Warning</b-badge>
        <b-badge variant="info">Info</b-badge>
        <b-badge variant="light">Light</b-badge>
        <b-badge variant="dark">Dark</b-badge>
        <p>This could be larger if I use buttons (should disable them):</p>
        <b-button-group>
        <b-button variant="primary">Primary</b-button>
        <b-button variant="secondary">Secondary</b-button>
        <b-button variant="success">Success</b-button>
        <b-button variant="danger">Danger</b-button>
        <b-button variant="warning">Warning</b-button>
        <b-button variant="info">Info</b-button>
        <b-button variant="light">Light</b-button>
        <b-button variant="dark">Dark</b-button>
        </b-button-group>
        
        <p>I could also use Modals to display relevant page information:</p>
        <b-button v-b-modal.modal-1>Launch demo modal</b-button>

        <b-modal id="modal-1" title="BootstrapVue">
            <p class="my-4">Hello from modal!</p>
        </b-modal>
        <p>Yet another interesting component to showcase project progress</p>
        <b-progress height="2rem" class="mt-2 w-50" :value="50" :max=100 show-progress animated></b-progress>
        <b-progress height="2rem" class="mt-2 w-50" :max=100 show-value>
            <b-progress-bar :value="50" variant="success"></b-progress-bar>
            <b-progress-bar :value="35" variant="warning"></b-progress-bar>
            <b-progress-bar :value="15" variant="danger"></b-progress-bar>
        </b-progress>
        <p>I would also like to create tabs to move around a project... something like this:</p>
        <div>
        <b-tabs content-class="mt-3">
            <b-tab title="First" active><p>I'm the first tab</p></b-tab>
            <b-tab title="Second"><p>I'm the second tab</p></b-tab>
            <b-tab title="Disabled" disabled><p>I'm a disabled tab!</p></b-tab>
        </b-tabs>
        </div>

        <b-tabs>
            <b-tab active>
            <template #title>
                <b-spinner type="grow" small></b-spinner> I'm <i>custom</i> <strong>title</strong>
            </template>
            <p class="p-3">Tab contents 1</p>
        </b-tab>

        <b-tab>
            <template #title>
                <b-spinner type="border" small></b-spinner> Tab 2
            </template>
            <p class="p-3">Tab contents 2</p>
            </b-tab>
        </b-tabs>
        Lazy loading of tabs could be an efficient way of doing this..
        <b-tabs content-class="mt-3" lazy>
            <b-tab title="Tab 1"><b-alert show>I'm lazy mounted!</b-alert></b-tab>
            <b-tab title="Tab 2"><b-alert show>I'm lazy mounted too!</b-alert></b-tab>
        </b-tabs>

    </div>

</template>


<script>
export default {
    name: 'ListProject',
    props: ['projects'],
    data() {
        return {
            form: {
                name: ""
            }
        }
    },
  computed: {
    sortProjects() {
        function compare(a,b) {
            if (a.name.toLowerCase() < b.name.toLowerCase()) {
                return -1
            }
            if (a.name.toLowerCase() > b.name.toLowerCase()) {
                return 1
            }
            return 0
        }
      return [...this.projects].sort(compare)
    }
  }
}

</script>