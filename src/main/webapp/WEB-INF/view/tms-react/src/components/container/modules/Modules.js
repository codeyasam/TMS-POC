import Modules from '../../ui/modules/Modules'
import { fetchModules } from '../../../actions/moduleActions'
import { connect } from 'react-redux'

const mapStateToProps = (state, props) => ({
    moduleList: state.module.moduleList
})

const mapDispatchToProps = dispatch => ({
    onFetchModules() {
        dispatch(fetchModules('', 1, 5))
    }
})

export default connect(mapStateToProps, mapDispatchToProps)(Modules)


