import CustomTableFooter from '../../ui/generic/CustomTableFooter'
import { connect } from 'react-redux'

const mapStateToProps = (state, props) => ({
    selectedEntriesCount: state.application.selectedApplicationEntries.length
})

const mapDispatchToProps = dispatch => ({
    
})

export default connect(mapStateToProps, mapDispatchToProps)(CustomTableFooter)