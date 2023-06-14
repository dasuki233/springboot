import VueRouter from "vue-router";

const routes = [
    {
        path: '/',
        name: 'login',
        component: () => import('../components/Login')
    },
    {
        path: '/Index',
        name: 'index',
        component: () => import('../components/Index'),
        children: [
            {
                path: '/Home',
                name: 'home',
                meta: {
                    title: '首页',
                },
                component: () => import('../components/Home')
            },
            // {
            //     path: '/admin/positionManagement/PostReview',
            //     name: 'postReview',
            //     meta:{
            //         title:'岗位审查',
            //     },
            //     component:()=>import('../components/admin/positionManagement/PostReview')
            // },
            // {
            //     path: '/admin/positionManagement/AddPosition',
            //     name: '123',
            //     meta:{
            //         title:'新增岗位',
            //     },
            //     component:()=>import('../components/admin/positionManagement/AddPosition')
            // },

        ]
    },

]

const router = new VueRouter({
    mode: 'history',
    routes

})

export function resetRouter() {
    router.matcher = new VueRouter({
        mode:'history',
        routes: []
    }).matcher
}


const VueRouterPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(to) {
    // console.log("routes")
    // console.log(routes)
    return VueRouterPush.call(this, to).catch(err => err)
}

export default router;