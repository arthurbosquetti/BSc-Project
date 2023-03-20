import {createRouter, createWebHistory} from 'vue-router'

import ListProject from './components/ListProject.vue'
import AddProject from './components/AddProject.vue'


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
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router