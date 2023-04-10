<template>
    <div>
        <h2>Test data for {{ projectName }}</h2>
        <table id="testDataEntryTable" class="table table-bordered table-striped">
            <thead>
            <tr>
                <th>Date</th>
                <th>Tests Passed</th>
                <th>Tests Failed</th>
                <th>Tests Blocked</th>
                <th>Tests No Run</th>
                <th>Tests Not Completed</th>
                <th>Total Tests</th>
                <th>95% Target</th>
                <th>Left to 95% Target</th>
                <th>Delete Entry</th>
            </tr>
            </thead>
            <tbody>
                <tr v-bind:key="entry.name"
                    v-for="entry in testDataEntries">
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
            this.axios
              .get(this.$backend.getUrlProjectTestDataList(projectName))
              .then(res => {
                this.testDataEntries = res.data
              })
        },
        deleteButton(entryDate) {
            this.axios
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