<template>
    <div>
        <h2>List of projects currently tracked</h2>
        <ul>
            <li
             v-bind:key="p.name"
             v-for="p in projects">
             {{ p.name }}
             <button @click="deleteProject(p.name)">Delete all data</button>
            </li>
        </ul>
    </div>

</template>


<script>
export default {
    name: 'ListProject',
    props: ['projects'],

    methods: {
        deleteProject(name) {
            this.axios
              .delete(this.$backend.getUrlDeleteProject(name))
              .then(() => {
                this.$emit('project-deleted')
              })
        }
    }

}

</script>