import Login from './views/Login.vue'
import NotFound from './views/404.vue'
import Home from './views/Home.vue'
import Main from './views/Main.vue'
import Controlleds from './views/nav1/Controlleds.vue'
import Scripts from './views/nav1/Scripts.vue'
import Table from './views/nav1/Table.vue'
import Form from './views/nav1/Form.vue'
import user from './views/nav1/user.vue'
import Masters from './views/nav2/Masters.vue'
import Page5 from './views/nav2/Page5.vue'
import Page6 from './views/nav3/Page6.vue'
import echarts from './views/charts/echarts.vue'

let routes = [
    {
        path: '/login',
        component: Login,
        name: '',
        hidden: true
    },
    {
        path: '/404',
        component: NotFound,
        name: '',
        hidden: true
    },
    //{ path: '/main', component: Main },
    {
        path: '/',
        component: Home,
        name: '受控端',
        iconCls: 'el-icon-message',//图标样式class
        children: [
            { path: '/controlleds', component: Controlleds, name: '终端列表'},
            //{ path: '/chat', component: Chat, name: 'Chat'},
            { path: '/scripts', component: Scripts, name: 'Chat'},
            { path: '/controlleds', component: Controlleds, name: '文件列表', hidden: true },
            { path: '/main', component: Main, name: '主页', hidden: true },
            { path: '/table', component: Table, name: 'Table' , hidden: true },
            { path: '/form', component: Form, name: 'Form' , hidden: true },
            { path: '/user', component: user, name: '列表' , hidden: true },
        ]
    },
    {
        path: '/',
        component: Home,
        name: '主控端',
        iconCls: 'fa fa-id-card-o',
        hidden : true,
        children: [
            { path: '/masters', component: Masters, name: '用户列表' },
            { path: '/page5', component: Page5, name: '页面5'  , hidden: true}
        ]
    },
    {
        path: '/',
        component: Home,
        name: '',
        iconCls: 'fa fa-address-card',
        leaf: true,//只有一个节点
        hidden : true,
        children: [
            { path: '/page6', component: Page6, name: '导航三' }
        ]
    },
    {
        path: '/',
        component: Home,
        name: 'Charts',
        iconCls: 'fa fa-bar-chart',
        hidden : true,
        children: [
            { path: '/echarts', component: echarts, name: 'echarts' }
        ]
    },
    {
        path: '*',
        hidden: true,
        redirect: { path: '/404' }
    }
];

export default routes;