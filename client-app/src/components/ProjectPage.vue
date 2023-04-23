<template>
    <div class="marginated">
        <b-tabs
        active-nav-item-class="font-weight-bold text-primary" content-class="mt-3">
            <b-tab>
                <template #title>
                    Overview<b-icon icon="info-circle" variant="primary" class="ml-2"></b-icon>
                </template>
                <p>Nothing to show here yet.</p>
            </b-tab>

            <b-tab>
                <template #title>
                    SV FFT Trends <b-icon icon="graph-up" variant="primary" class="ml-2"></b-icon>
                </template>
                <GraphsTestDataEntries />
            </b-tab>
            
            <b-tab>
                <template #title>
                    Bug Trends <b-icon icon="graph-down" variant="primary" class="ml-2"></b-icon>
                </template>
                <GraphsBugDataEntries />
            </b-tab>

            <b-tab>
                <template #title>
                    SV FFT Data <b-icon icon="clipboard-data" variant="primary" class="ml-2"></b-icon>
                </template>
                <ListTestDataEntries />
            </b-tab>

            <b-tab>
                <template #title>
                    Bug Data <b-icon icon="clipboard-data" variant="primary" class="ml-2"></b-icon>
                </template>
                <ListBugDataEntries />
            </b-tab>

            <b-tab>
                <template #title>
                    Settings <b-icon icon="gear" variant="primary" class="ml-2"></b-icon>
                </template>
                <p>Nothing to show here yet.</p>
            </b-tab>

        </b-tabs>

    </div>
</template>

<script>
import GraphsBugDataEntries from './GraphsBugDataEntries.vue';
import GraphsTestDataEntries from './GraphsTestDataEntries.vue';
import ListTestDataEntries from './ListTestDataEntries.vue';
import ListBugDataEntries from './ListBugDataEntries.vue';

export default {
    name: "ProjectPage",
    components: { GraphsTestDataEntries, GraphsBugDataEntries, ListTestDataEntries, ListBugDataEntries },
    data() {
        return {
            projectName: "",
        }
    },
    async mounted() {
        this.projectName = this.$route.params.projectName;
    }
}
</script>