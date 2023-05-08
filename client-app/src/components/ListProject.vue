<template>
    <div class="marginated">
        <b-container fluid>
            <b-row>

                <!-- Filter -->
                <b-col cols="3">
                    <b-input-group size="md" class="mb-2">
                        <b-input-group-prepend is-text>
                            <b-icon icon="search" variant="dark"></b-icon>
                        </b-input-group-prepend>
                        <b-form-input
                        id="filter-input"
                        v-model="filter"
                        type="search"
                        placeholder="Click to Search"
                        ></b-form-input>
                    </b-input-group>
                    
                </b-col>

                <!-- Filter on -->
                <b-col class="text-center" cols="6">
                    <b-form-group
                    class="mb-0"
                    description="Leave all unchecked to filter on all data"
                    >
                    <b-form-checkbox-group
                        v-model="filterOn"
                        class="mt-1"
                    >
                        <b-form-checkbox value="name">Project Name</b-form-checkbox>
                        <b-form-checkbox value="fftDeadline">FFT Deadline</b-form-checkbox>
                        <b-form-checkbox value="isActive">Is Active</b-form-checkbox>
                        <b-form-checkbox value="fftStatus">FFT Status</b-form-checkbox>
                        <b-form-checkbox value="svApproved">SV Approved</b-form-checkbox>
                    </b-form-checkbox-group>
                    </b-form-group>
                </b-col>
                                
                <!-- Items per page -->
                <b-col cols="1">
                    <b-form-select
                            id="per-page-select"
                            v-model="perPage"
                            :options="pageOptions"
                            size="md"
                    ></b-form-select>
                </b-col>

                <!-- Pagination -->
                <b-col cols="2">
                    <b-pagination
                    v-model="currentPage"
                    :total-rows="totalRows"
                    :per-page="perPage"
                    align="fill"
                    size="md"
                    class="my-0"
                    >
                        <template #first-text><b-icon icon="chevron-double-left"></b-icon></template>
                        <template #prev-text><b-icon icon="chevron-left"></b-icon></template>
                        <template #next-text><b-icon icon="chevron-right"></b-icon></template>
                        <template #last-text><b-icon icon="chevron-double-right"></b-icon></template>
                    </b-pagination>
                </b-col>


            </b-row>
            
            <p><b>Total number of projects: </b> {{ totalRows }}</p>

            <b-table
            :items="items"
            :fields="fields"
            :current-page="currentPage"
            :per-page="perPage"
            :filter="filter"
            :filter-included-fields="filterOn"
            :sort-by.sync="sortBy"
            :sort-desc.sync="sortDesc"
            :sort-direction="sortDirection"
            striped
            bordered
            sticky-header="600px" 
            show-empty
            @filtered="onFiltered"
            hover
            selectable
            @row-selected="onRowSelected"
            >
            <template #cell(isActive)="row">
                <h5 class="mb-0">
                    <b-badge pill :variant="row.value ? 'success' : 'warning'">{{ row.value }}</b-badge>
                </h5>
            </template>
            <template #cell(fftStatus)="row">
                <h5 class="mb-0">
                    <b-badge pill :variant="variants[row.value]">{{ row.value }}</b-badge>
                </h5>
            </template>
            <template #cell(svApproved)="row">
                <h5 class="mb-0">
                    <b-badge pill :variant="row.value ? 'success' : 'danger'">{{ row.value }}</b-badge>
                </h5>
            </template>
            </b-table>
        </b-container>        
    </div>

</template>


<script>
import router from '@/router'

export default {
    name: 'ListProject',
    data() {
        return {
            projects: [],
            items: [],
            fields: [
                { key: 'name', label: 'Project Name', sortable: true},
                { key: 'fftDeadline', label: 'FFT Deadline', sortable: true},
                { key: 'isActive', sortable: true},
                { key: 'fftStatus', label: 'FFT Status', sortable: true},
                { key: 'svApproved', label: 'SV Approved', sortable: true},
                { key: 'daysRecording', label: 'Days Recording', sortable: true},
                { key: 'entryCount', label: 'Number of Entries', sortable: true}
            ],
            totalRows: 0,
            currentPage: 1,
            perPage: 10,
            pageOptions: [10, 25, 50, 100],
            sortBy: 'name',
            sortDesc: false,
            sortDirection: 'asc',
            filter: null,
            filterOn: [],
            variants: {
                'COMPLETE': 'success',
                'AHEAD': 'success',
                'ON_TRACK': 'info',
                'BEHIND': 'warning',
                'CRITICAL': 'danger',
                'INCOMPLETE': 'danger',
                'UNDEFINED': 'secondary'
            }
        }
    },
    computed: {
        sortOptions() {
            return this.fields
            .filter(f => f.sortable)
            .map(f => {
                return { text: f.label, value: f.key }
            })
        }
    },
    async mounted() {
        await this.fetch()
        this.items = this.generateTableItems()
        this.totalRows = this.items.length
    },
    methods: {
        async fetch() {
            await this.$axios
                .get(this.$backend.getUrlProjectList())
                .then(res => {
                this.projects = res.data
            })
        },
        generateTableItems() {
            let items = []
            for (let i = 0; i < this.projects.length; i++) {
                let project = this.projects[i]
                items.push({
                    'name': project['name'],
                    'fftDeadline': project['fftDeadline'],
                    'isActive': project['isActive'],
                    'fftStatus': project['fftStatus'],
                    'svApproved': project['svApproved'],
                    'daysRecording': Math.max(project['testDataEntries'].length, project['fftDataEntries'].length, project['bugDataEntries'].length),
                    'entryCount': project['testDataEntries'].length + project['bugDataEntries'].length + project['fftDataEntries'].length
                })
            }

            return items
        },
        onFiltered(filteredItems) {
            this.totalRows = filteredItems.length
            this.currentPage = 1
        },
        onRowSelected(items) {
            router.push({
                name: 'ProjectPage',
                params: {projectName: items[0].name}
            })
        }
    }
}

</script>

<style>

.nowrap {
  white-space: nowrap;
}

div .container-fluid {
    padding: 0px;
}

</style>