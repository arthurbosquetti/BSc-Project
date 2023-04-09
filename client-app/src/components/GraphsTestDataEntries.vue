<template>
    <div>
        <h2>SV FFT (Full Functional Testing) Trends for {{ projectName }}</h2>
        <canvas ref="dailyTrend" style="max-width: 1200px; max-height: 600px;"/>
    </div>
  </template> 

<script>

import Chart from "chart.js/auto"

export default {
    name: 'GraphsTestDataEntries',
    components: {},
    data() {
        return {
            projectName: null,
            testDataEntries: [],
            labels: [],
            datasets: [],
            // TODO: add target95
            // fields: ['testsPassed', 'testsFailed', 'testsBlocked', 'testsNoRun', 'testsNotCompleted'] //, 'target95'],
        } 
    },    
    async mounted() {
        this.projectName = this.$route.params.projectName
        await this.fetch(this.projectName)
        this.labels = this.generateLabels()
        this.datasets = this.generateDatasets()
        this.renderChart("dailyTrend")
    },
    methods: {
        async fetch(projectName) {
            await this.axios.get(this.$backend.getUrlProjectTestDataList(projectName))
            .then(res => {
                this.testDataEntries = res.data
              })
        },
        generateLabels() {
            let labels = []
            for (let i = 0; i < this.testDataEntries.length; i++) {
                let label = this.testDataEntries[i]['dataEntryId']['entryDate']
                labels.push(label)
            }
            return labels
        },
        generateDatasets() {
            let testsPassedDataset = []
            let testsFailedDataset = []
            let testsBlockedDataset = []
            let testsNotCompletedDataset = []
            let testsNoRunDataset = []
            let target95Dataset = []
            
            for (let i = 0; i < this.testDataEntries.length; i++) {
                testsPassedDataset.push(this.testDataEntries[i]["testsPassed"])
                testsFailedDataset.push(this.testDataEntries[i]["testsFailed"])
                testsBlockedDataset.push(this.testDataEntries[i]["testsBlocked"])
                testsNotCompletedDataset.push(this.testDataEntries[i]["testsNotCompleted"])
                testsNoRunDataset.push(this.testDataEntries[i]["testsNoRun"])
                target95Dataset.push(this.testDataEntries[i]["target95"])
            }
            
            let datasets = [] 
            datasets.push({
                "label": "Passed",
                "data": testsPassedDataset,
                "backgroundColor": "rgba(159, 207, 63, 1)",
                "order": 1
                })
            datasets.push({
                "label": "Failed",
                "data": testsFailedDataset,
                "backgroundColor": "rgba(207, 63, 88, 1)",
                "order": 2
                })
            datasets.push({
                "label": "Blocked",
                "data": testsBlockedDataset,
                "backgroundColor": "rgba(42, 213, 234, 1)",
                "order": 3
                })
            datasets.push({
                "label": "Not Completed",
                "data": testsNotCompletedDataset,
                "backgroundColor": "rgba(102, 16, 242, 1)",
                "order": 4
                })
            datasets.push({
                "label": "No Run",
                "data": testsNoRunDataset,
                "backgroundColor": "rgba(255, 255, 136, 1)",
                "order": 5
                })
            datasets.push({
                "label": "95% Pass Goal",
                "data": target95Dataset,
                "type": "line",
                "pointBorderColor": "rgba(0,0,0, 1)",
                "pointBackgroundColor": "rgba(159, 207, 63, 1)",
                "backgroundColor": "rgba(159, 207, 63, 1)",
                "borderColor": "rgba(159, 207, 63, 1)",
                "borderDash": [10,10],
                "pointRadius": 6,
                "order": 0
                })
            return datasets
        },
        renderChart(ref) {
            new Chart(this.$refs[ref].getContext("2d"), {
                type: 'bar',
                data: {
                    labels: this.labels,
                    datasets: this.datasets
                },
                options: {
                    plugins: {
                        title: {
                            display: true,
                            text: "SV FFT Daily Trends for " + this.projectName
                        },
                        legend: {
                            display: true,
                            position: "bottom"
                        },
                        labels: {
                            display: true,
                            position: "bottom"
                        }
                    },
                    responsive: true,
                    maintainAspectRatio: false,
                    scales: {
                        x: {
                            stacked: true,
                        },
                        y: {
                            stacked: true
                        }
                    }
                }
            })
        }
    }
}
</script>

<style>
    .chart-container {
        display: flex;
        justify-content: space-between;
        margin-top: 20px;
    }
</style>