<template>
    <div>
        <h2>Bug data for {{ projectName }}</h2>
        <table class="table table-bordered table-striped">
            <thead class="table-secondary header">
            <tr>
                <th></th>
                <th></th>
                <th colspan="5"><h5>Open Bugs</h5></th>
                <th colspan="5"><h5>Fixed Bugs (not verified)</h5></th>
                <th rowspan="2" style="vertical-align : middle;text-align:center;"><h5>Delete Entry</h5></th>
            </tr>
            <tr>
                <th><h5>Date</h5></th>
                <th><h5>SW Version</h5></th>
                <th><h5>Blocker Bugs</h5></th>
                <th><h5>Critical Bugs</h5></th>
                <th><h5>Major Bugs</h5></th>
                <th><h5>Minor Bugs</h5></th>
                <th><h5>Trivial Bugs</h5></th>
                <th><h5>Blocker Bugs</h5></th>
                <th><h5>Critical Bugs</h5></th>
                <th><h5>Major Bugs</h5></th>
                <th><h5>Minor Bugs</h5></th>
                <th><h5>Trivial Bugs</h5></th>
            </tr>
            </thead>
            <tbody>
                <tr v-bind:key="entry.name"
                    v-for="entry in bugDataEntries">
                    <td>{{ entry['dataEntryId']['entryDate']}}</td>
                    <td v-for="field in fields" :key='field'>{{ entry[field] }}</td>    
                    <td><button @click="deleteButton(entry['dataEntryId']['entryDate'])">Delete Entry</button></td>           
                </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
export default {
    name: 'ListBugDataEntries',
    data() {
        return {
            projectName: null,
            bugDataEntries: [],
            fields: ['swVersion', 'openBlockerBugs', 'openCriticalBugs', 'openMajorBugs', 'openMinorBugs', 'openTrivialBugs', 'fixedBlockerBugs', 'fixedCriticalBugs','fixedMajorBugs','fixedMinorBugs','fixedTrivialBugs']
        }
    },
    methods: {
        fetch(projectName) {
            this.$axios
              .get(this.$backend.getUrlProjectBugDataList(projectName))
              .then(res => {
                this.bugDataEntries = res.data
              })
        },
        deleteButton(entryDate) {
            this.$axios
              .delete(this.$backend.getUrlDeleteBugDataEntry(this.projectName, entryDate))
              .catch(function (error) {
                if (error.response) {
                    window.alert(error.response.data);
                }
              })
              .then(() => {
                const entryIndex = this.bugDataEntries.findIndex(bugDataEntries => bugDataEntries.dataEntryId.entryDate === entryDate)
                if (~entryIndex)
                    this.bugDataEntries.splice(entryIndex, 1)
              })            
        }
    },
    mounted() {
        this.projectName = this.$route.params.projectName
        this.fetch(this.projectName)
        console.log("Fetched data for " + this.projectName)
    }
}

</script>

<style>

.header{
        position:sticky;
        top: 0 ;
    }

</style>