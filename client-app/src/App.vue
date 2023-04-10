<template>
  <div>
    <img src="./assets/shure_logo.png" style="max-width:420px;max-height:100px;">
    <h1>Project Management</h1>
    <h2>Options</h2>
    <ol>
      <li><router-link :to="{ name: 'ListProject' }">View list of projects currently tracked</router-link></li>
      <li><router-link :to="{ name: 'AddProject' }">Track new project</router-link></li>
      <li><router-link :to="{ name: 'DeleteProject' }">Delete a project and all its data</router-link></li>
    </ol>

    <router-view 
      :projects="this.projects"
      @new-project="refresh"
      @project-deleted="refresh"/>
  </div>
</template>

<script>
import router from './router'

export default {
  name: 'App',
  components: { },
  data() {
    return {
      projects: []
    }
  },
  methods: {
    refresh() {
      this.axios
        .get(this.$backend.getUrlProjectList())
        .then(res => {
          this.projects = res.data
          router.push({ name: 'ListProject' })
      })
    }
  },
  mounted() {
    this.refresh()
  }
}
</script>

<style>
* {
  margin:0.25%;
  font-family: "Segoe UI", sans-serif; 
}
.container {
 display: grid;
 /* align-items: center;  */
 grid-template-columns: 1fr 1fr 1fr;
 column-gap: 5px;
}

img {
  max-width: 50%;
  max-height:50%;
}

</style>
