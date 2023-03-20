import {createRouter, createWebHistory} from 'vue-router'

import ListProject from './components/ListProject.vue'
import AddProject from './components/AddProject.vue'
import DeleteProject from './components/DeleteProject.vue'
import ListTestDataEntries from './components/ListTestDataEntries.vue'
import ListBugDataEntries from './components/ListBugDataEntries.vue'

const routes = [
    {
        path: '/',
        component: ListProject,
        name: 'ListProject'
    },
    {
        path: '/add-project',
        component: AddProject,
        name: 'AddProject'
    },
    {
        path: '/delete-project',
        component: DeleteProject,
        name: 'DeleteProject'
    },
    {
        path: '/project/:projectName/test-data',
        component: ListTestDataEntries,
        name: 'ListTestDataEntries'
    },
    {
        path: '/project/:projectName/bug-data',
        component: ListBugDataEntries,
        name: 'ListBugDataEntries'
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router