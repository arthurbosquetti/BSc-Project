<template>
    <div>
        <p class="vspace"></p>
        <b-container fluid>
            <b-row>

                <!-- Filter -->
                <b-col>
                    <b-input-group size="md" class="mb-2">
                        <b-input-group-prepend is-text>
                            <b-icon icon="search" variant="dark"></b-icon>
                        </b-input-group-prepend>
                        <b-form-input
                        id="filter-input"
                        v-model="filter"
                        type="search"
                        placeholder="Type to Search"
                        ></b-form-input>
                    </b-input-group>
                    
                </b-col>

                <!-- Filter on -->
                <b-col class="text-center">
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
                        <b-form-checkbox value="status">Status</b-form-checkbox>
                    </b-form-checkbox-group>
                    </b-form-group>
                </b-col>
                                
                <!-- Items per page -->
                <b-col>
                    <b-form-group
                    label="Entries per page"
                    label-for="per-page-select"
                    label-cols-sm="6"
                    label-cols-md="4"
                    label-align-sm="right"
                    label-size="md"
                    class="mb-0 nowrap"
                    >
                        <b-form-select
                            id="per-page-select"
                            v-model="perPage"
                            :options="pageOptions"
                            size="md"
                        ></b-form-select>
                    </b-form-group>
                </b-col>

                <!-- Pagination -->
                <b-col>
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
            
            <p class="vspace"></p>

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
            class="w-75">
            </b-table>
        </b-container>        
    </div>

</template>


<script>
import router from '@/router'

export default {
    name: 'ListProject',
    // props: ['projects'],
    data() {
        return {
            projects: [],
            items: [],
            fields: [
                { key: 'name', label: 'Project Name', sortable: true, sortDirection: 'desc' },
                { key: 'fftDeadline', label: 'FFT Deadline', sortable: true},
                { key: 'status', sortable: true}
            ],
            totalRows: 1,
            currentPage: 1,
            perPage: 10,
            pageOptions: [10, 25, 50, 100],
            sortBy: '',
            sortDesc: false,
            sortDirection: 'asc',
            filter: null,
            filterOn: []
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
                items.push({
                    'name': this.projects[i]['name'],
                    'fftDeadline': this.projects[i]['fftDeadline'],
                    'status': this.projects[i]['status']
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
                name: 'ListBugDataEntries',
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

.vspace {
     margin-bottom: 15px;
  }


</style>