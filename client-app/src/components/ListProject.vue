<template>
    <div>
        <h2>List of projects currently tracked</h2>
        <ul>
            <li
             v-bind:key="p.name"
             v-for="p in sortProjects">
             {{ p.name }} -
            <router-link :to="{ name: 'ListTestDataEntries', params: { projectName: p.name }}">Test data entries (Raw)</router-link> -
            <router-link :to="{ name: 'GraphsTestDataEntries', params: { projectName: p.name }}">Test data entries (Graphs)</router-link> -
            <router-link :to="{ name: 'ListBugDataEntries', params: { projectName: p.name }}">Bug data entries (Raw)</router-link>
            </li>
        </ul>
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