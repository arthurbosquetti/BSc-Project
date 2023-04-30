<template>
    <div>
        <b-button
        v-b-toggle="'collapse-2'"
        class="mt-2" 
        :variant="variant"
        block
        style="text-align: left"
        @click="toggleCollapse">
            <b>FFT Complete Graph</b><b-icon :icon="collapseOn ? 'eye-slash' : 'eye'" class="ml-2" variant="light"></b-icon>
        </b-button>
        <b-collapse id="collapse-2" visible>
            <b-card v-show="validData" >
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
                <canvas ref="FFTComplete" style="max-width: 1200px; max-height: 600px;"/>
                <b-button variant="outline-primary" @click="downloadFigure">
                    <b-icon icon="download"></b-icon> Download Figure
                </b-button>
            </b-card>
            <b-card v-show="!validData">
                <p>There are no records to show. Please come back later.</p>
            </b-card>
        </b-collapse>
        
    </div>

</template>

<script>
import { Chart } from 'chart.js'

export default {
    name: 'GraphFFTComplete',
    props: {
        testDataEntries: Array,
        fftDeadline: String,
        variant: String
    },
    data() {
        return {
            projectName: '',
            chart: {},
            chartData: {},
            validData: false,
            collapseOn: true,
            variantToRBG: {
                "success": "#28a745",
                "info": "#17a2b8",
                "warning": "#ffc107",
                "danger": "#dc3545" 
            },
            startDate: '',
            endDate: '',
            minDate: '',
            maxDate: '',
        }
    },
    mounted() {
        if (this.testDataEntries.length == 0 || this.fftDeadline == null || this.fftDeadline =='') {
            return
        }
        
        this.validData = true
        this.minDate = this.testDataEntries[0].dataEntryId.entryDate
        this.startDate = this.minDate
        this.maxDate = this.fftDeadline
        this.endDate = this.maxDate
        this.projectName = this.$route.params.projectName
        this.chartData = this.generateGraphData()
        this.renderChart()
    },
    methods: {
        toggleCollapse() {
            this.collapseOn = !this.collapseOn
        },
        generateGraphData() {
            let labels = []
            let actualLeft = []

            let startDate = new Date(this.testDataEntries[0]["dataEntryId"]["entryDate"])
            let endDate = new Date(this.fftDeadline)
            let projectDuration = (endDate - startDate)/(24*60*60*1000) + 1
            let totalTests = 0
            let idealRate = 0
            let idealLeft = []

            for (let i = 0; i < projectDuration; i++) {
                if (i < this.testDataEntries.length) {
                    let testDataEntry = this.testDataEntries[i]
                    totalTests = testDataEntry["totalTests"]
                    idealRate = totalTests/projectDuration
                    actualLeft.push(testDataEntry["testsPassed"])
                }
                idealLeft.push(Math.ceil((i+1)*idealRate))
                labels.push(startDate.toISOString().substring(0,10))
                startDate.setDate(startDate.getDate() + 1)
            }

            let datasets = []
            datasets.push(
                {
                    "label": "Actual Passed",
                    "data": actualLeft,
                    "backgroundColor": this.variantToRBG[this.variant],
                    "borderColor": this.variantToRBG[this.variant]
                },
                {
                    "label": "Expected Passed",
                    "data": idealLeft,
                    "backgroundColor": "rgba(159, 207, 63, 1)",
                    "borderColor": "rgba(159, 207, 63, 1)"
                }
            )

            return {"labels": labels, "datasets": datasets}

        },
        renderChart() {
            this.chart = new Chart(this.$refs["FFTComplete"].getContext("2d"), {
                type: 'line',
                data: {
                    labels: this.chartData.labels,
                    datasets: this.chartData.datasets
                },
                options: {
                    plugins: {
                        title: {
                            display: true,
                            text: "SV 100% FFT Completion for " + this.projectName
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
                    maintainAspectRatio: false
                }
            })

        },
        downloadFigure() {
            var a = document.createElement('a');
            a.href = this.chart.toBase64Image();
            a.download = (this.projectName + "_" + this.chart.options.plugins.title.text.replace(" for " + this.projectName, "")).replaceAll(" ", "_") +'.png';
            a.click()
        },
        updateCharts() {
            this.chart.options.scales.x.min = this.startDate
            this.chart.options.scales.x.max = this.endDate
            this.chart.update()
        }
    }
}
</script>

