<template>
    <div>
        <p><b>Number of entries: </b> {{ items.length }}</p>
            <b-table striped bordered sticky-header="600px" :items="items" :fields="fields" show-empty></b-table>
    </div>
</template>

<script>
export default {
    name: 'ListBugDataEntries',
    data() {
        return {
            projectName: '',
            bugDataEntries: [],
            items: [],            
            fields: [
                { key: 'entryDate', label: 'Date'},
                { key: 'swVersion', label: 'SW Version'},
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
        async fetch(projectName) {
            await this.$axios
              .get(this.$backend.getUrlProjectBugDataList(projectName))
              .then(res => {
                this.bugDataEntries = res.data
              })
        },
        generateTableItems() {
            let items = []
            for (let i = 0; i < this.bugDataEntries.length; i++) {
                let bugDataEntry = this.bugDataEntries[i]
                items.push({
                    'entryDate': bugDataEntry['dataEntryId']['entryDate'],
                    'swVersion': bugDataEntry['swVersion'],
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
    async mounted() {
        this.projectName = this.$route.params.projectName
        await this.fetch(this.projectName)
        this.items = this.generateTableItems()
        this.bugDataEntries = []
    }
}

</script>

<style>

.header{
        position:sticky;
        top: 0 ;
    }

</style>