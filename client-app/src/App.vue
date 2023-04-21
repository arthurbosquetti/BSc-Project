<template>
  <div>
    <!-- <img src="./assets/shure_logo.png" style="max-width:400px;max-height:100px;"> -->
    <h1 class="display-3">A management tool for planning and tracking project status</h1>
    
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
      <!-- <b-navbar-brand href=https://shure.sharepoint.com/sites/frequency>
          <img src="./assets/logo.png" class="d-inline-block align-top" alt="Shure" style="max-width:30px;max-height:30px;">
          Shure
      </b-navbar-brand> -->
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
          <ul class="navbar-nav me-auto mb-2 mb-lg-0">
            <li class="nav-item">
              <router-link :to="{ name: 'HomePage' }" class="nav-link">Home</router-link>
            </li>
            <li class="nav-item">
              <router-link :to="{ name: 'ReleaseNotes' }" class="nav-link">Release Notes</router-link>
            </li>
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
        </div>
    </nav>

    <router-view
      :projects="this.projects"
      @new-project="render"
      @project-deleted="render"/>

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
    render() {
      router.push({ name: 'HomePage' }).catch(() => {})
    }
  },
  mounted() {
    this.render()
  }
}
</script>

<style>
* {
  font-family: "Segoe UI", sans-serif;
}

body {
  padding:15px;
}

nav ul li .nav-link {
  color: #FFF;
  padding: 10px;
  white-space: nowrap;
  border-bottom: 5px solid transparent;
  display: flex;
  transition: 0.4s;
}

nav .navbar-brand {
  white-space: nowrap;
  border-bottom: 9px solid transparent;
}

nav .nav-link.active,
nav .nav-link:hover {
  border-bottom: 5px solid #b2ff33;
}

</style>
