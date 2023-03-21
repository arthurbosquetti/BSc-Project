<template>
  <div>
    <h1>Project Management</h1>

    <h3>Options</h3>
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
  font-family: 'Arial', sans-serif;
}
</style>
