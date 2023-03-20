<template>
    <div>
        <h2>List of projects currently tracked</h2>
        <ul>
            <li
             v-bind:key="p.name"
             v-for="p in projects">
             {{ p.name }} -
            <router-link :to="{ name: 'ListTestDataEntries', params: { projectName: p.name }}">Test data entries</router-link> -
            <router-link :to="{ name: 'ListBugDataEntries', params: { projectName: p.name }}">Bug data entries</router-link>
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
    }
}

</script>