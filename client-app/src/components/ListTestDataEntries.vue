<template>
    <div>
        <h2>Test data for {{ projectName }}</h2>
        <p><b>Number of entries: </b> {{ testDataEntries.length }}</p>
        <div class="w-75">
            <b-table striped small bordered sticky-header="600px" :items="items" :fields="fields"></b-table>
        </div>
    </div>
</template>

<script>
export default {
    name: 'ListTestDataEntries',
    data() {
        return {
            projectName: null,
            testDataEntries: [],
            items: [],
            fields: [
                { key: 'entryDate', label: 'Date'},
                'testsPassed',
                'testsFailed',
                'testsBlocked',
                'testsNoRun',
                'testsNotCompleted',
                'passRate',
                { key: 'target95', label: "95% Goal"},
                { key: 'leftToTarget', label: "Left to 95% Goal"},
                'totalTests'
        ]
        }
    },
    methods: {
        async fetch(projectName) {
            await this.$axios
              .get(this.$backend.getUrlProjectTestDataList(projectName))
              .then(res => {
                this.testDataEntries = res.data
              })
        },
        // deleteButton(entryDate) {
        //     this.$axios
        //       .delete(this.$backend.getUrlDeleteTestDataEntry(this.projectName, entryDate))
        //       .catch(function (error) {
        //         if (error.response) {
        //             window.alert(error.response.data);
        //         }
        //       })
        //       .then(() => {
        //         const entryIndex = this.testDataEntries.findIndex(testDataEntries => testDataEntries.dataEntryId.entryDate === entryDate)
        //         if (~entryIndex)
        //             this.testDataEntries.splice(entryIndex, 1)
        //       }) 
        // }
        generateTableItems() {
            let items = []
            for (let i = 0; i < this.testDataEntries.length; i++) {
                items.push({
                    'entryDate': this.testDataEntries[i]['dataEntryId']['entryDate'],
                    'testsPassed': this.testDataEntries[i]['testsPassed'],
                    'testsFailed': this.testDataEntries[i]['testsFailed'],
                    'testsBlocked': this.testDataEntries[i]['testsBlocked'],
                    'testsNoRun': this.testDataEntries[i]['testsNoRun'],
                    'testsNotCompleted': this.testDataEntries[i]['testsNotCompleted'],
                    'passRate': parseFloat(this.testDataEntries[i]['testsPassed']/this.testDataEntries[i]['totalTests'] * 100).toFixed(2) + "%",
                    'target95': this.testDataEntries[i]['target95'],
                    'leftToTarget': this.testDataEntries[i]['leftToTarget'],
                    'totalTests': this.testDataEntries[i]['totalTests']
                })
            }

            return items

        }
    },
    async mounted() {
        this.projectName = this.$route.params.projectName
        await this.fetch(this.projectName)
        this.items = this.generateTableItems()
    }
}

</script>

<style>

.header{
        position:sticky;
        top: 0 ;
    }

</style>