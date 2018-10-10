import CustomTableFooter from '../../ui/generic/CustomTableFooter'
import { showAddApplicationForm, hideErrorOnAddingApplication, showEditApplicationForm, hideErrorOnEditingApplication, showDeleteApplicationForm, hideErrorOnDeletingApplication } from '../../../actions'
import { connect } from 'react-redux'

const mapStateToProps = (state, props) => ({
    selectedEntriesCount: state.application.selectedApplicationEntries.length
})

const mapDispatchToProps = dispatch => ({
    onAddButtonClick() {
        dispatch(hideErrorOnAddingApplication())
        dispatch(showAddApplicationForm())
    },
    onEditButtonClick() {
        dispatch(hideErrorOnEditingApplication())
        dispatch(showEditApplicationForm())
    },
    onDeleteButtonClick() {
        dispatch(hideErrorOnDeletingApplication())
        dispatch(showDeleteApplicationForm())
    }
})

export default connect(mapStateToProps, mapDispatchToProps)(CustomTableFooter)