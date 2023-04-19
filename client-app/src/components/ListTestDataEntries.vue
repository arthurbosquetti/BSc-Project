<template>
    <div>
        <h2>Test data for {{ projectName }}</h2>
        <table class="table table-bordered table-striped">
            <thead class="table-secondary header">
            <tr>
                <th><h5>Date</h5></th>
                <th><h5>Tests Passed</h5></th>
                <th><h5>Tests Failed</h5></th>
                <th><h5>Tests Blocked</h5></th>
                <th><h5>Tests No Run</h5></th>
                <th><h5>Tests Not Completed</h5></th>
                <th><h5>Total Tests</h5></th>
                <th><h5>95% Target</h5></th>
                <th><h5>Left to 95% Target</h5></th>
                <th><h5>Pass Rate</h5></th>
                <th><h5>Delete Entry</h5></th>
            </tr>
            </thead>
            <tbody>
                <tr v-bind:key="entry.name"
                    v-for="entry in testDataEntries">
                    <td>{{ entry['dataEntryId']['entryDate']}}</td>
                    <td v-for="field in fields" :key='field'>{{ entry[field] }}</td>
                    <td>{{ (Number(entry['testsPassed'])/Number(entry['totalTests'])*100).toFixed(2) }} %</td>   
                    <td><button @click="deleteButton(entry['dataEntryId']['entryDate'])">Delete Entry</button></td>                       
                </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
export default {
    name: 'ListTestDataEntries',
    data() {
        return {
            projectName: null,
            testDataEntries: [],
            fields: ['testsPassed', 'testsFailed', 'testsBlocked', 'testsNoRun', 'testsNotCompleted', 'totalTests', 'target95', 'leftToTarget']
        }
    },
    methods: {
        fetch(projectName) {
            this.$axios
              .get(this.$backend.getUrlProjectTestDataList(projectName))
              .then(res => {
                this.testDataEntries = res.data
              })
        },
        deleteButton(entryDate) {
            this.$axios
              .delete(this.$backend.getUrlDeleteTestDataEntry(this.projectName, entryDate))
              .catch(function (error) {
                if (error.response) {
                    window.alert(error.response.data);
                }
              })
              .then(() => {
                const entryIndex = this.testDataEntries.findIndex(testDataEntries => testDataEntries.dataEntryId.entryDate === entryDate)
                if (~entryIndex)
                    this.testDataEntries.splice(entryIndex, 1)
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