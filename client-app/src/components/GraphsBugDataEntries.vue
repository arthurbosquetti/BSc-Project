<template>
    <div>
        <canvas ref="dailyBCMMTrend" style="max-width: 1200px; max-height: 600px;"/>
        <canvas ref="weeklyBCMMTrend" style="max-width: 1200px; max-height: 600px;"/>
        <canvas ref="dailyBCMTrend" style="max-width: 1200px; max-height: 600px;"/>
        <canvas ref="weeklyBCMTrend" style="max-width: 1200px; max-height: 600px;"/>
    </div>
</template>

<script>
import Chart from "chart.js/auto"

export default {
    name: 'GraphsBugDataEntries',
    data() {
        return {
            projectName: '',
            bugDataEntries: [],
            dailyBCMMTrendData: {},
            weeklyBCMMTrendData: {},
            dailyBCMTrendData: {},
            weeklyBCMTrendData: {}
        }
    },
    async mounted() {
        this.projectName = this.$route.params.projectName
        await this.fetch(this.projectName)

        this.dailyBCMMTrendData = this.generateGraphData(1, ["Blocker", "Critical", "Major", "Minor"])
        this.weeklyBCMMTrendData = this.generateGraphData(7, ["Blocker", "Critical", "Major", "Minor"])
        this.dailyBCMTrendData = this.generateGraphData(1, ["Blocker", "Critical", "Major"])
        this.weeklyBCMTrendData = this.generateGraphData(7, ["Blocker", "Critical", "Major", "Minor"])
        
        this.renderChart("dailyBCMMTrend", this.dailyBCMMTrendData, "BCMM Bugs Daily Trend for " + this.projectName)
        this.renderChart("weeklyBCMMTrend", this.weeklyBCMMTrendData, "BCMM Bugs Weekly Trend for " + this.projectName)
        this.renderChart("dailyBCMTrend", this.dailyBCMTrendData, "BCM Bugs Daily Trend for " + this.projectName)
        this.renderChart("weeklyBCMTrend", this.weeklyBCMTrendData, "BCM Bugs Weekly Trend for " + this.projectName)

    },
    methods: {
        async fetch(projectName) {
            await this.$axios.get(this.$backend.getUrlProjectBugDataList(projectName))
            .then(res => {
                this.bugDataEntries = res.data
              })
        },
        generateGraphData(dayInterval, bugLevels) {
            let labels = []

            let openBugTrend = []
            let fixedBugTrend = []

            for (let i = 0; i < this.bugDataEntries.length; i += dayInterval) {
                let bugDataEntry = this.bugDataEntries[i]
                let entryDate = bugDataEntry["dataEntryId"]["entryDate"]
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