<template>
    <div>
        <b-button
        v-b-toggle="'collapse-2'"
        class="mt-2" variant="light"
        :pressed="true"
        block
        style="text-align: left"
        @click="toggleCollapse">
            <b>FFT Complete Graph</b><b-icon :icon="collapseOn ? 'eye-slash' : 'eye'" class="ml-2" variant="dark"></b-icon>
        </b-button>
        <b-collapse id="collapse-2" visible text-align="center">
            <b-card v-show="validData" >
                <canvas ref="FFTComplete" style="max-width: 800px; max-height: 400px;"/>
                <b-button variant="outline-primary" @click="downloadFigure">
                    <b-icon icon="download"></b-icon> Download Figure
                </b-button>
            </b-card>
            <b-card v-if="!validData">
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
            }
        }
    },
    mounted() {
        this.projectName = this.$route.params.projectName
        if (!(this.testDataEntries.length > 0 && this.fftDeadline != null && this.fftDeadline !='')) {
            return
        }
        this.validData = true
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

            let totalTests = this.testDataEntries[0]["totalTests"]
            let startDate = new Date(this.testDataEntries[0]["dataEntryId"]["entryDate"])
            let endDate = new Date(this.fftDeadline)
            let projectDuration = Math.abs(endDate - startDate) / (24*60*60*1000)
            let idealRate = totalTests/projectDuration
            let idealLeft = []

            for (let i = 0; i <= projectDuration; i++) {
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
                    "data": actualLeft,
                    "backgroundColor": this.variantToRBG[this.variant],
                    "borderColor": this.variantToRBG[this.variant]
                },
                {
                    "label": "Ideal Left",
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

