<template>
    <div>
        <b-button v-b-toggle="'collapse-2'" class="mt-2" variant="light" style="max-width: 1200px;">FFT Complete Graph<b-icon icon="eye" class="ml-2"></b-icon></b-button>
        <b-collapse id="collapse-2" style="max-width: 1200px;" visible>
            <b-card v-show="validData" >
                <canvas ref="FFTComplete" style="max-width: 1200px; max-height: 600px;"/>
                <b-button variant="outline-primary" @click="downloadFigure">
                    <b-icon icon="download"></b-icon> Download Figure
                </b-button>
            </b-card>
            <b-card v-if="!validData">
                <p>Data is not valid!</p>
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
        fftDeadline: String
    },
    data() {
        return {
            projectName: '',
            chart: {},
            chartData: {},
            validData: false
        }
    },
    mounted() {
        this.projectName = this.$route.params.projectName
        // console.log(this.testDataEntries.length > 0)
        // console.log(this.testDataEntries.length != null)
        // console.log(this.fftDeadline !='')
        // console.log(this.testDataEntries.length > 0 && this.fftDeadline != null && this.fftDeadline !='')
        if (!(this.testDataEntries.length > 0 && this.fftDeadline != null && this.fftDeadline !='')) {
            return
        }
        this.validData = true
        this.chartData = this.generateGraphData()
        this.renderChart()
    },
    methods: {
        generateGraphData() {
            let labels = []
            let actualLeft = []

            let totalTests = this.testDataEntries[0]["totalTests"]
            let startDate = new Date(this.testDataEntries[0]["dataEntryId"]["entryDate"])
            let endDate = new Date(this.fftDeadline)
            let projectDuration = Math.abs(endDate - startDate) / (24*60*60*1000)
            let idealRate = totalTests/projectDuration
            let idealLeft = []

            for (let i = 0; i < projectDuration; i++) {
                if (i < this.testDataEntries.length) {
                    let testDataEntry = this.testDataEntries[i]
                    totalTests = testDataEntry['totalTests']
                    actualLeft.push(totalTests - testDataEntry['testsPassed'])
                }
                idealLeft.push(Math.floor(totalTests - i*idealRate))
                labels.push(startDate.toISOString().substring(0,10))
                startDate.setDate(startDate.getDate() + 1)
            }

            let datasets = []
            datasets.push(
                {
                    "label": "Actual Left",
                    "data": actualLeft
                },
                {
                    "label": "Ideal Left",
                    "data": idealLeft
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
                            text: "SV FFT Completion for " + this.projectName
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
                }
            })

        },
        downloadFigure() {
            console.log(this.chart)
            var a = document.createElement('a');
            a.href = this.chart.toBase64Image();
            a.download = (this.projectName + "_" + this.chart.options.plugins.title.text.replace(" for " + this.projectName, "")).replaceAll(" ", "_") +'.png';
            a.click()
        }
    }
}
</script>

