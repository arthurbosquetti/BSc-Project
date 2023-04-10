<template>
    <div>
        <h2>Bug data for {{ projectName }}</h2>
        <table id="bugDataEntryTable" class="table table-bordered table-striped">
            <thead>
            <tr>
                <th></th>
                <th></th>
                <th colspan="5">Open Bugs</th>
                <th colspan="5">Fixed Bugs (not verified)</th>
            </tr>
            <tr>
                <th>Date</th>
                <th>SW Version</th>
                <th>Blocker Bugs</th>
                <th>Critical Bugs</th>
                <th>Major Bugs</th>
                <th>Minor Bugs</th>
                <th>Trivial Bugs</th>
                <th>Blocker Bugs</th>
                <th>Critical Bugs</th>
                <th>Major Bugs</th>
                <th>Minor Bugs</th>
                <th>Trivial Bugs</th>
                <th>Delete Entry</th>
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
            this.axios
              .get(this.$backend.getUrlProjectBugDataList(projectName))
              .then(res => {
                this.bugDataEntries = res.data
              })
        },
        deleteButton(entryDate) {
            this.axios
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