<template>
  <div>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
          <ul class="navbar-nav me-auto mb-2 mb-lg-0">
            <li class="nav-item">
              <router-link :to="{ name: 'HomePage' }" class="nav-link">Home<b-icon icon="house" class="ml-2"></b-icon></router-link>
            </li>
            <li class="nav-item">
              <router-link :to="{ name: 'ReleaseNotes' }" class="nav-link">Release Notes<b-icon icon="file-earmark" class="ml-2"></b-icon></router-link>
            </li>
            <li class="nav-item">
              <router-link :to="{ name: 'ListProject' }" class="nav-link">All Projects<b-icon icon="clipboard-data" class="ml-2"></b-icon></router-link>
            </li>
            <li class="nav-item">
              <router-link :to="{ name: 'AddProject' }" class="nav-link">Add Project<b-icon icon="plus-square" class="ml-2"></b-icon></router-link>
            </li>
          </ul>
        </div>
    </nav>
    <p></p>

    <router-view/>

  </div>
</template>


<script>

export default {
  name: 'App'
}
</script>

<style>
* {
  font-family: "Segoe UI", sans-serif;
}

.marginated {
  margin-left:15px;
  margin-bottom: 40px;
  margin-right: 15px;
}

nav ul li .nav-link {
  color: #FFF !important;
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

nav ul li .nav-link.active,
nav ul li .nav-link:hover {
  border-bottom: 5px solid #b2ff33;
}

</style>
