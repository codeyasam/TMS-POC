import CustomTable from '../../ui/generic/CustomTable'
import { connect } from 'react-redux'

const mapStateToProps = (state, props) => ({
    applications: state.applications
})

const mapDispatchToProps = dispatch => ({
    
})

export default connect(mapStateToProps, mapDispatchToProps)(CustomTable)