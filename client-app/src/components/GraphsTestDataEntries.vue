<template>
    <div>
        <div v-show="validData">
            <b-container fluid>
                <b-row style="max-width: 1200px" align-v="end">
                    <b-col>
                        <label label-for="start-date-picker">Select a start date</label>
                        <b-form-datepicker
                        button-variant="secondary"
                        dark
                        nav-button-variant="light"
                        close-button
                        close-button-variant="light"
                        reset-button
                        reset-button-variant="warning"
                        :reset-value="minDate"
                        :min="minDate"
                        :max="endDate"
                        no-highlight-today
                        v-model="startDate"
                        id="start-date-picker"
                        ></b-form-datepicker>
                    </b-col>
                    <b-col>
                        <label label-for="end-date-picker">Select an end date</label>
                        <b-form-datepicker
                        button-variant="secondary"
                        dark
                        nav-button-variant="light"
                        close-button
                        close-button-variant="light"
                        reset-button
                        reset-button-variant="warning"
                        :reset-value="maxDate"
                        :min="startDate"
                        :max="maxDate"
                        no-highlight-today
                        v-model="endDate"
                        id="end-date-picker"
                        ></b-form-datepicker>
                    </b-col>
                        <b-button variant="outline-primary" style="max-height: 50px;" @click="updateCharts">
                            <b-icon icon="arrow-clockwise"></b-icon> Update graphs 
                        </b-button>
                </b-row>
            </b-container>
            <canvas ref="dailyTrend" style="max-width: 1200px; max-height: 600px;"/>
            <b-button variant="outline-primary" @click="downloadFigure('dailyTrend')">
                <b-icon icon="download"></b-icon> Download Figure
            </b-button>
            
            <canvas ref="weeklyTrend" style="max-width: 1200px; max-height: 600px;"/>
            <b-button variant="outline-primary" @click="downloadFigure('weeklyTrend')">
                <b-icon icon="download"></b-icon> Download Figure
            </b-button>
        </div>
        <p v-show="!validData">Nothing to show. Please come back later.</p>
    </div>
  </template> 

<script>
import Chart from "chart.js/auto"

export default {
    name: 'GraphsTestDataEntries',
    props: {
        testDataEntries: Array
    },
    data() {
        return {
            projectName: '',
            dailyTrendData: {},
            weeklyTrendData: {},
            charts: {},
            validData: false,
            startDate: '',
            endDate: '',
            minDate: '',
            maxDate: '',
        } 
    },    
    mounted() {
        if (!this.testDataEntries.length > 0) {
            return
        }
        
        this.validData = true
        this.startDate = this.testDataEntries[0].dataEntryId.entryDate
        this.minDate = this.startDate
        this.endDate = this.testDataEntries[this.testDataEntries.length - 1].dataEntryId.entryDate
        this.maxDate = this.endDate
        this.projectName = this.$route.params.projectName

        this.dailyTrendData = this.generateGraphData(this.minDate, this.maxDate, 1)
        this.weeklyTrendData = this.generateGraphData(this.minDate, this.maxDate, 7)
        this.renderChart("dailyTrend", this.dailyTrendData, "SV FFT Daily Trend for " + this.projectName)
        this.renderChart("weeklyTrend", this.weeklyTrendData, "SV FFT Weekly Trend for " + this.projectName)
    },
    methods: {
        generateGraphData(startDate, endDate, interval) {
            let labels = []

            let testsPassedDataset = []
            let testsFailedDataset = []
            let testsBlockedDataset = []
            let testsNotCompletedDataset = []
            let testsNoRunDataset = []
            let target95Dataset = []

            let startIndex = 0

            for (let i = 0; i < this.testDataEntries.length; i++) {
                let testDataEntry = this.testDataEntries[i]
                let entryDate = testDataEntry["dataEntryId"]["entryDate"]
                if (entryDate < startDate) {
                    continue
                }
                if (entryDate == startDate) {
                    startIndex = i
                }

                if (entryDate > endDate) {
                    break
                }
                if ((i-startIndex)%interval != 0) {
                    continue
                }

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
        },
        updateCharts() {
            this.charts["dailyTrend"].data = this.generateGraphData(this.startDate, this.endDate, 1)
            this.charts["weeklyTrend"].data= this.generateGraphData(this.startDate, this.endDate, 7)

            for (const [, value] of Object.entries(this.charts)) {
                value.update()
            }
        }
    }
}
</script>
