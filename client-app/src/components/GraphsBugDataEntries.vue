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
            <canvas ref="dailyBCMMTrend" style="max-width: 1200px; max-height: 600px;"/>
            <b-button variant="outline-primary" @click="downloadFigure('dailyBCMMTrend')">
                <b-icon icon="download"></b-icon> Download Figure
            </b-button>

            <canvas ref="weeklyBCMMTrend" style="max-width: 1200px; max-height: 600px;"/>
            <b-button variant="outline-primary" @click="downloadFigure('weeklyBCMMTrend')">
                <b-icon icon="download"></b-icon> Download Figure
            </b-button>

            <canvas ref="dailyBCMTrend" style="max-width: 1200px; max-height: 600px;"/>
            <b-button variant="outline-primary" @click="downloadFigure('dailyBCMTrend')">
                <b-icon icon="download"></b-icon> Download Figure
            </b-button>

            <canvas ref="weeklyBCMTrend" style="max-width: 1200px; max-height: 600px;"/>
            <b-button variant="outline-primary" @click="downloadFigure('weeklyBCMTrend')">
                <b-icon icon="download"></b-icon> Download Figure
            </b-button>
        </div>
        <p v-show="!validData">Nothing to show. Please come back later.</p>
    </div>
</template>

<script>
import Chart from "chart.js/auto"

export default {
    name: 'GraphsBugDataEntries',
    props: {
        bugDataEntries: Array
    },
    data() {
        return {
            projectName: '',
            dailyBCMMTrendData: {},
            weeklyBCMMTrendData: {},
            dailyBCMTrendData: {},
            weeklyBCMTrendData: {},
            charts: {},
            validData: false,
            startDate: '',
            endDate: '',
            minDate: '',
            maxDate: '',
        }
    },
    mounted() {
        if (!this.bugDataEntries.length > 0) {
            return
        }
        
        this.validData = true
        this.startDate = this.bugDataEntries[0].dataEntryId.entryDate
        this.minDate = this.startDate
        this.endDate = this.bugDataEntries[this.bugDataEntries.length - 1].dataEntryId.entryDate
        this.maxDate = this.endDate
        this.projectName = this.$route.params.projectName

        this.dailyBCMMTrendData = this.generateGraphData(this.startDate, this.endDate, 1, ["Blocker", "Critical", "Major", "Minor"])
        this.weeklyBCMMTrendData = this.generateGraphData(this.startDate, this.endDate, 7, ["Blocker", "Critical", "Major", "Minor"])
        this.dailyBCMTrendData = this.generateGraphData(this.startDate, this.endDate, 1, ["Blocker", "Critical", "Major"])
        this.weeklyBCMTrendData = this.generateGraphData(this.startDate, this.endDate, 7, ["Blocker", "Critical", "Major"])

        this.renderChart("dailyBCMMTrend", this.dailyBCMMTrendData, "BCMM Bugs Daily Trend for " + this.projectName)
        this.renderChart("weeklyBCMMTrend", this.weeklyBCMMTrendData, "BCMM Bugs Weekly Trend for " + this.projectName)
        this.renderChart("dailyBCMTrend", this.dailyBCMTrendData, "BCM Bugs Daily Trend for " + this.projectName)
        this.renderChart("weeklyBCMTrend", this.weeklyBCMTrendData, "BCM Bugs Weekly Trend for " + this.projectName)
    },
    methods: {
        generateGraphData(startDate, endDate, interval, bugLevels) {
            let labels = []

            let openBugTrend = []
            let fixedBugTrend = []

            let startIndex = 0

            for (let i = 0; i < this.bugDataEntries.length; i++) {
                let bugDataEntry = this.bugDataEntries[i]
                let entryDate = bugDataEntry["dataEntryId"]["entryDate"]
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

                let openBugsInEntry = 0
                let fixedBugsInEntry = 0

                for (let level of bugLevels) {
                    openBugsInEntry += bugDataEntry["open" + level + "Bugs"]
                    fixedBugsInEntry += bugDataEntry["fixed" + level + "Bugs"]
                }

                openBugTrend.push(openBugsInEntry)
                fixedBugTrend.push(fixedBugsInEntry)

            }

            let graphAcronym = ''
            for (let i = 0; i < bugLevels.length; i++) {
                graphAcronym += bugLevels[i].charAt(0)
            }

            let datasets = []
            datasets.push(
                {
                    "label": "Open " + graphAcronym,
                    "data": openBugTrend,
                    "backgroundColor": "rgba(207, 63, 88, 1)"
                },
                {
                    "label": "Fixed (not verified) " + graphAcronym,
                    "data": fixedBugTrend,
                    "backgroundColor": "rgba(159, 207, 63, 1)",
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
            this.charts["dailyBCMMTrend"].data = this.generateGraphData(this.startDate, this.endDate, 1, ["Blocker", "Critical", "Major", "Minor"])
            this.charts["weeklyBCMMTrend"].data = this.generateGraphData(this.startDate, this.endDate, 7, ["Blocker", "Critical", "Major", "Minor"])
            this.charts["dailyBCMTrend"].data = this.generateGraphData(this.startDate, this.endDate, 1, ["Blocker", "Critical", "Major"])
            this.charts["weeklyBCMTrend"].data = this.generateGraphData(this.startDate, this.endDate, 7, ["Blocker", "Critical", "Major"])

            for (const [, value] of Object.entries(this.charts)) {
                value.update()
            }
        }
    }
}
</script>