/**
 * Created by admin on 2018/4/17.
 */
/**

 @Name��layuiAdmin iframe��ȫ������
 @Author������
 @Site��http://www.layui.com/admin/
 @License��LPPL��layui���Ѳ�ƷЭ�飩

 */

layui.define(['laytpl', 'layer', 'element', 'util'], function (exports) {
    exports('setter', {
        container: 'LAY_app' //����ID
        , base: layui.cache.base //��¼��̬��Դ����·��
        , views: layui.cache.base + 'tpl/' //��̬ģ������Ŀ¼
        , entry: 'index' //Ĭ����ͼ�ļ���
        , engine: '.html' //��ͼ�ļ���׺��
        , pageTabs: true //�Ƿ���ҳ��ѡ����ܡ�iframe ������Ƽ�����

        , name: 'layuiAdmin'
        , tableName: 'layuiAdmin' //���ش洢����
        , MOD_NAME: 'admin' //ģ���¼���

        , debug: true //�Ƿ�������ģʽ���翪�����ӿ��쳣ʱ���׳��쳣 URL ����Ϣ

        //�Զ��������ֶ�
        , request: {
            tokenName: false //�Զ�Я�� token ���ֶ������磺access_token���������� false ��Я����
        }

        //�Զ�����Ӧ�ֶ�
        , response: {
            statusName: 'code' //����״̬���ֶ�����
            , statusCode: {
                ok: 0 //����״̬һ��������״̬��
                , logout: 1001 //��¼״̬ʧЧ��״̬��
            }
            , msgName: 'msg' //״̬��Ϣ���ֶ�����
            , dataName: 'data' //����������ֶ�����
        }

        //��չ�ĵ�����ģ��
        , extend: [
            'echarts', //echarts ���İ�
            'echartsTheme' //echarts ����
        ]

        //��������
        , theme: {
            //��ɫ����������û�δ�������⣬��һ������ΪĬ��
            color: [{
                main: '#20222A' //����ɫ
                , selected: '#009688' //ѡ��ɫ
                , alias: 'default' //Ĭ�ϱ���
            }, {
                main: '#03152A'
                , selected: '#3B91FF'
                , alias: 'dark-blue' //����
            }, {
                main: '#2E241B'
                , selected: '#A48566'
                , alias: 'coffee' //����
            }, {
                main: '#50314F'
                , selected: '#7A4D7B'
                , alias: 'purple-red' //�Ϻ�
            }, {
                main: '#344058'
                , logo: '#1E9FFF'
                , selected: '#1E9FFF'
                , alias: 'ocean' //����
            }, {
                main: '#3A3D49'
                , logo: '#2F9688'
                , selected: '#5FB878'
                , alias: 'green' //ī��
            }, {
                main: '#20222A'
                , logo: '#F78400'
                , selected: '#F78400'
                , alias: 'red' //��ɫ
            }, {
                main: '#28333E'
                , logo: '#AA3130'
                , selected: '#AA3130'
                , alias: 'fashion-red' //ʱ�к�
            }, {
                main: '#24262F'
                , logo: '#3A3D49'
                , selected: '#009688'
                , alias: 'classic-black' //�����
            }]
        }
    });
});
