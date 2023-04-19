<template>
  <div>
    <img src="./assets/shure_logo.png" style="max-width:400px;max-height:100px;">
    <h1>Project Management</h1>

    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
      <div class="container-fluid">
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
          <ul class="navbar-nav me-auto mb-2 mb-lg-0">
            <li class="nav-item">
              <router-link :to="{ name: 'ListProject' }" class="nav-link">All Projects</router-link>
            </li>
            <li class="nav-item">
              <router-link :to="{ name: 'AddProject' }" class="nav-link">Add Project</router-link>
            </li>
            <li class="nav-item">
              <router-link :to="{ name: 'DeleteProject' }" class="nav-link">Delete Project</router-link>
            </li>
          </ul>
          <form class="d-flex">
            <input class="form-control me-3" type="search" placeholder="Search Projects" aria-label="Search">
            <button class="btn btn-outline-light me-3" type="submit">Search</button>
          </form>
        </div>
      </div>
    </nav>

    <!-- <h2>Options</h2>
    <ol>
      <li><router-link :to="{ name: 'ListProject' }" >View list of projects currently tracked</router-link></li>
      <li><router-link :to="{ name: 'AddProject' }">Track new project</router-link></li>
      <li><router-link :to="{ name: 'DeleteProject' }">Delete a project and all its data</router-link></li>
    </ol> -->

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
      this.$axios
        .get(this.$backend.getUrlProjectList())
        .then(res => {
          this.projects = res.data
          router.push({ name: 'ListProject' }).catch(() => {})
      })
    },
  },
  mounted() {
    this.refresh()
  }
}
</script>

<style>
* {
  margin:0.15%;
  font-family: "Segoe UI", sans-serif;
}

nav div ul li .nav-link {
  color: #FFF;
  padding: 10px;
  white-space: nowrap;
  border-bottom: 5px solid transparent;
  display: flex;
  transition: 0.4s;
}

nav .nav-link.active,
nav .nav-link:hover {
  border-bottom: 5px solid #b2ff33;
}

</style>
