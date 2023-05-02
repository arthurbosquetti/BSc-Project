<template>
    <div>
        <p><b>Number of entries: </b> {{ items.length }}</p>
            <b-table striped bordered sticky-header="600px" :items="items" :fields="fields" show-empty></b-table>
    </div>
</template>

<script>
export default {
    name: 'ListBugDataEntries',
    props: {
        bugDataEntries: {
            type: Array,
            default: () => []
        }
    },
    data() {
        return {
            projectName: '',
            items: [],            
            fields: [
                { key: 'entryDate', label: 'Date'},
                'openBlockerBugs',
                'openCriticalBugs',
                'openMajorBugs',
                'openMinorBugs',
                'openTrivialBugs',
                'fixedBlockerBugs',
                'fixedCriticalBugs',
                'fixedMajorBugs',
                'fixedMinorBugs',
                'fixedTrivialBugs'
            ]
        }
    },
    methods: {
        generateTableItems() {
            let items = []
            for (let i = 0; i < this.bugDataEntries.length; i++) {
                let bugDataEntry = this.bugDataEntries[i]
                items.push({
                    'entryDate': bugDataEntry['dataEntryId']['entryDate'],
                    'openBlockerBugs': bugDataEntry['openBlockerBugs'],
                    'openCriticalBugs': bugDataEntry['openCriticalBugs'],
                    'openMajorBugs': bugDataEntry['openMajorBugs'],
                    'openMinorBugs': bugDataEntry['openMinorBugs'],
                    'openTrivialBugs': bugDataEntry['openTrivialBugs'],
                    'fixedBlockerBugs': bugDataEntry['fixedBlockerBugs'],
                    'fixedCriticalBugs': bugDataEntry['fixedCriticalBugs'],
                    'fixedMajorBugs': bugDataEntry['fixedMajorBugs'],
                    'fixedMinorBugs': bugDataEntry['fixedMinorBugs'],
                    'fixedTrivialBugs': bugDataEntry['fixedTrivialBugs'],
                })
            }
            return items
        }
    },
    mounted() {
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