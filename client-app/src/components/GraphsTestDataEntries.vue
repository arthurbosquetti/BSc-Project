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
            fields: ['testsPassed', 'testsFailed', 'testsBlocked', 'testsNoRun', 'testsNotCompleted'] //, 'target95'],
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
            let datasets = []
            for (let f = 0; f < this.fields.length; f++) {
                let data = []
                for (let i = 0; i < this.testDataEntries.length; i++) {
                    data.push(this.testDataEntries[i][this.fields[f]])
                }
                datasets.push({
                    "label": this.fields[f],
                    "data": data,
                    // "backgroundColor": "rbga(255, 19, 132, 0.2)",
                    // "borderColor": "rgba(255, 19, 132, 1)",
                    "borderWidth": 1
                })
            }
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
                        }
                    },
                    responsive: true,
                    maintainAspectRatio: false,
                    scales: {
                        x: {
                            stacked: true
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