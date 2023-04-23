<template>
    <div class="marginated">
        <b-tabs
        active-nav-item-class="font-weight-bold text-primary" content-class="mt-3">
            <b-tab title="Project Overview"><p>Nothing to show here yet.</p></b-tab>

            <b-tab title="SV FFT Trends">
                <GraphsTestDataEntries />
            </b-tab>
            
            <b-tab title="Bug Trends">
                <GraphsBugDataEntries />
            </b-tab>

            <b-tab title="SV FFT Data Table">
                Add something to export the data?
                <ListTestDataEntries />
            </b-tab>
            <b-tab title="Bug Data Table">
                Add something to export the data?
                <ListBugDataEntries />
            </b-tab>
            <b-tab title="Settings">
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