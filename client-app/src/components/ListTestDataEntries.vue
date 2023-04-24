<template>
    <div>
        <p><b>Number of entries: </b> {{ items.length }}</p>
        <b-table striped bordered sticky-header="600px" :items="items" :fields="fields" show-empty></b-table>
    </div>
</template>

<script>
export default {
    name: 'ListTestDataEntries',
    props: {
        testDataEntries: {
            type: Array,
            default: () => []
        }
    },
    data() {
        return {
            projectName: '',
            // testDataEntries: [],
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
        generateTableItems() {
            let items = []
            for (let i = 0; i < this.testDataEntries.length; i++) {
                let testDataEntry = this.testDataEntries[i]
                items.push({
                    'entryDate': testDataEntry['dataEntryId']['entryDate'],
                    'testsPassed': testDataEntry['testsPassed'],
                    'testsFailed': testDataEntry['testsFailed'],
                    'testsBlocked': testDataEntry['testsBlocked'],
                    'testsNoRun': testDataEntry['testsNoRun'],
                    'testsNotCompleted': testDataEntry['testsNotCompleted'],
                    'passRate': parseFloat(testDataEntry['testsPassed']/testDataEntry['totalTests'] * 100).toFixed(2) + "%",
                    'target95': testDataEntry['target95'],
                    'leftToTarget': testDataEntry['leftToTarget'],
                    'totalTests': testDataEntry['totalTests']
                })
            }

            return items

        }
    },
    async mounted() {
        this.projectName = this.$route.params.projectName
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