<template>
    <div class="marginated">
        <b-tabs
        active-nav-item-class="font-weight-bold text-primary" content-class="mt-3">
            <b-tab>
                <template #title>
                    Overview<b-icon icon="info-circle" variant="primary" class="ml-2"></b-icon>
                </template>
                <ProjectOverview />
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
                <ProjectSettings @patch-project="reloadPage" @delete-project="deleteProject"/>
            </b-tab>

        </b-tabs>

    </div>
</template>

<script>
import GraphsBugDataEntries from './GraphsBugDataEntries.vue';
import GraphsTestDataEntries from './GraphsTestDataEntries.vue';
import ListTestDataEntries from './ListTestDataEntries.vue';
import ListBugDataEntries from './ListBugDataEntries.vue';
import ProjectOverview from './ProjectOverview.vue';
import ProjectSettings from './ProjectSettings.vue';
import router from '@/router';

export default {
    name: "ProjectPage",
    components: { GraphsTestDataEntries, GraphsBugDataEntries, ListTestDataEntries, ListBugDataEntries, ProjectOverview, ProjectSettings },
    data() {
        return {
            projectName: "",
        }
    },
    methods: {
        reloadPage() {
            window.location.reload()
        },
        deleteProject() {
            router.push
        }
    },
    async mounted() {
        this.projectName = this.$route.params.projectName;
    }
}
</script>