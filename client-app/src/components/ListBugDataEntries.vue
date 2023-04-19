<template>
    <div>
        <h2>Bug data for {{ projectName }}</h2>
        <p><b>Number of entries: </b> {{ bugDataEntries.length }}</p>
        <div class="w-75">
            <b-table striped small bordered sticky-header="600px" :items="items" :fields="fields"></b-table>
        </div>
    </div>
</template>

<script>
export default {
    name: 'ListBugDataEntries',
    data() {
        return {
            projectName: null,
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
        // deleteButton(entryDate) {
        //     this.$axios
        //       .delete(this.$backend.getUrlDeleteBugDataEntry(this.projectName, entryDate))
        //       .catch(function (error) {
        //         if (error.response) {
        //             window.alert(error.response.data);
        //         }
        //       })
        //       .then(() => {
        //         const entryIndex = this.bugDataEntries.findIndex(bugDataEntries => bugDataEntries.dataEntryId.entryDate === entryDate)
        //         if (~entryIndex)
        //             this.bugDataEntries.splice(entryIndex, 1)
        //       })            
        // },
        generateTableItems() {
            let items = []
            for (let i = 0; i < this.bugDataEntries.length; i++) {
                items.push({
                    'entryDate': this.bugDataEntries[i]['dataEntryId']['entryDate'],
                    'swVersion': this.bugDataEntries[i]['swVersion'],
                    'openBlockerBugs': this.bugDataEntries[i]['openBlockerBugs'],
                    'openCriticalBugs': this.bugDataEntries[i]['openCriticalBugs'],
                    'openMajorBugs': this.bugDataEntries[i]['openMajorBugs'],
                    'openMinorBugs': this.bugDataEntries[i]['openMinorBugs'],
                    'openTrivialBugs': this.bugDataEntries[i]['openTrivialBugs'],
                    'fixedBlockerBugs': this.bugDataEntries[i]['fixedBlockerBugs'],
                    'fixedCriticalBugs': this.bugDataEntries[i]['fixedCriticalBugs'],
                    'fixedMajorBugs': this.bugDataEntries[i]['fixedMajorBugs'],
                    'fixedMinorBugs': this.bugDataEntries[i]['fixedMinorBugs'],
                    'fixedTrivialBugs': this.bugDataEntries[i]['fixedTrivialBugs'],
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