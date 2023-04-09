<template>
    <div>
        <h2>SV FFT (Full Functional Testing) Trends for {{ projectName }}</h2>
        <canvas ref="dailyTrend" style="max-width: 1200px; max-height: 600px;"/>
        <canvas ref="weeklyTrend" style="max-width: 1200px; max-height: 600px;"/>
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
            dailyTrendData: {},
            weeklyTrendData: {}
        } 
    },    
    async mounted() {
        this.projectName = this.$route.params.projectName
        await this.fetch(this.projectName)
        this.dailyTrendData = this.generateGraphData(1)
        this.weeklyTrendData = this.generateGraphData(7)
        this.renderChart("dailyTrend", this.dailyTrendData, "SV FFT Daily Trends for " + this.projectName)
        this.renderChart("weeklyTrend", this.weeklyTrendData, "SV FFT Weekly Trends for " + this.projectName)
    },
    methods: {
        async fetch(projectName) {
            await this.axios.get(this.$backend.getUrlProjectTestDataList(projectName))
            .then(res => {
                this.testDataEntries = res.data
              })
        },
        generateGraphData(dayInterval) {
            let labels = []

            let testsPassedDataset = []
            let testsFailedDataset = []
            let testsBlockedDataset = []
            let testsNotCompletedDataset = []
            let testsNoRunDataset = []
            let target95Dataset = []

            for (let i = 0; i < this.testDataEntries.length; i += dayInterval) {
                let entryDate = this.testDataEntries[i]["dataEntryId"]["entryDate"]
                labels.push(entryDate)

                testsPassedDataset.push(this.testDataEntries[i]["testsPassed"])
                testsFailedDataset.push(this.testDataEntries[i]["testsFailed"])
                testsBlockedDataset.push(this.testDataEntries[i]["testsBlocked"])
                testsNotCompletedDataset.push(this.testDataEntries[i]["testsNotCompleted"])
                testsNoRunDataset.push(this.testDataEntries[i]["testsNoRun"])
                target95Dataset.push(this.testDataEntries[i]["target95"])
            }
            
            let datasets = [] 
            datasets.push(
                {
                    "label": "Passed",
                    "data": testsPassedDataset,
                    "backgroundColor": "rgba(159, 207, 63, 1)",
                    "order": 1
                },
                {
                    "label": "Failed",
                    "data": testsFailedDataset,
                    "backgroundColor": "rgba(207, 63, 88, 1)",
                    "order": 2
                },
                {
                    "label": "Blocked",
                    "data": testsBlockedDataset,
                    "backgroundColor": "rgba(42, 213, 234, 1)",
                    "order": 3
                },
                {
                    "label": "Not Completed",
                    "data": testsNotCompletedDataset,
                    "backgroundColor": "rgba(102, 16, 242, 1)",
                    "order": 4
                },
                {
                    "label": "No Run",
                    "data": testsNoRunDataset,
                    "backgroundColor": "rgba(255, 255, 136, 1)",
                    "order": 5
                },
                {
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
            return {"labels": labels, "datasets": datasets}
        },
        renderChart(ref, data, titleText) {
            new Chart(this.$refs[ref].getContext("2d"), {
                type: 'bar',
                data: {
                    labels: data.labels,
                    datasets: data.datasets
                },
                options: {
                    plugins: {
                        title: {
                            display: true,
                            text: titleText
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
