<template>
    <div>
        <b-form-group
        id="input-group-project-name"
        label="Project Name"
        label-for="input-project-name"
        >
            <b-input-group size="md" class="mb-2">
                <b-input-group-prepend is-text>
                    <b-icon icon="bookmark" variant="primary"></b-icon>
                </b-input-group-prepend>
                <b-form-input
                id="input-project-name"
                :value="project['name']"
                type="text"
                disabled
                ></b-form-input>
            </b-input-group>
        </b-form-group>

        <b-form-group
            id="input-group-nittany-url"
            label="Nittany Report URL"
            label-for="input-nittany-url"
        >
            <b-input-group class="mb-2">
                <b-input-group-prepend is-text>
                    <b-icon icon="link45deg" variant="primary"></b-icon>
                </b-input-group-prepend>
                <b-form-input
                id="input-nittany-url"
                v-model="{...project}['nittanyUrl']"
                type="url"
                disabled
                ></b-form-input>
            </b-input-group>    
        </b-form-group>
        <label label-for="fftGraph">100% FFT Completion Graph</label>
        <GraphFFTComplete
        id="fftGraph"
        :fftDataEntries="project['fftDataEntries']"
        :fftDeadline="project['fftDeadline']" v-if="variant" :variant="variant"/>
    </div>
</template>

<script>
import GraphFFTComplete from './GraphFFTComplete.vue';

export default {
    name: 'ProjectOverview',
    components: { GraphFFTComplete },
    props: {
        project: Object
    },
    data() {
        return {
            variants: {
                'COMPLETE': 'success',
                'AHEAD': 'success',
                'ON_TRACK': 'info',
                'BEHIND': 'warning',
                'CRITICAL': 'danger',
                'INCOMPLETE': 'danger',
                'UNDEFINED': 'secondary'
            },
            variant: '',
        }
    },
    mounted() {
        this.variant = this.variants[this.project['fftStatus']]
    }
}
</script>