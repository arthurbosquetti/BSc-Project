<template>
    <div>
        <canvas ref="dailyTrend" style="max-width: 1200px; max-height: 600px;"/>
        <b-button variant="outline-primary" @click="downloadFigure('dailyTrend')">
            <b-icon icon="download"></b-icon> Download Figure
        </b-button>
        
        <canvas ref="weeklyTrend" style="max-width: 1200px; max-height: 600px;"/>
        <b-button variant="outline-primary" @click="downloadFigure('weeklyTrend')">
            <b-icon icon="download"></b-icon> Download Figure
        </b-button>

        
    </div>
  </template> 

<script>
import Chart from "chart.js/auto"

export default {
    name: 'GraphsTestDataEntries',
    props: {
        testDataEntries: {
            type: Array,
            default: () => []
        }
    },
    data() {
        return {
            projectName: '',
            dailyTrendData: {},
            weeklyTrendData: {},
            charts: {}
        } 
    },    
    mounted() {
        this.projectName = this.$route.params.projectName
        this.dailyTrendData = this.generateGraphData(1)
        this.weeklyTrendData = this.generateGraphData(7)
        this.renderChart("dailyTrend", this.dailyTrendData, "SV FFT Daily Trend for " + this.projectName)
        this.renderChart("weeklyTrend", this.weeklyTrendData, "SV FFT Weekly Trend for " + this.projectName)
    },
    methods: {
        generateGraphData(dayInterval) {
            let labels = []

            let testsPassedDataset = []
            let testsFailedDataset = []
            let testsBlockedDataset = []
            let testsNotCompletedDataset = []
            let testsNoRunDataset = []
            let target95Dataset = []

            for (let i = 0; i < this.testDataEntries.length; i += dayInterval) {
                let testDataEntry = this.testDataEntries[i]
                let entryDate = testDataEntry["dataEntryId"]["entryDate"]
                labels.push(entryDate)

                testsPassedDataset.push(testDataEntry["testsPassed"])
                testsFailedDataset.push(testDataEntry["testsFailed"])
                testsBlockedDataset.push(testDataEntry["testsBlocked"])
                testsNotCompletedDataset.push(testDataEntry["testsNotCompleted"])
                testsNoRunDataset.push(testDataEntry["testsNoRun"])
                target95Dataset.push(testDataEntry["target95"])
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
            this.charts[ref] = new Chart(this.$refs[ref].getContext("2d"), {
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
        },
        downloadFigure(ref) {
            var chart = this.charts[ref] 
            var a = document.createElement('a');
            a.href = chart.toBase64Image();
            a.download = (this.projectName + "_" + chart.options.plugins.title.text.replace(" for " + this.projectName, "")).replaceAll(" ", "_") +'.png';
            a.click()
        }
    }
}
</script>
