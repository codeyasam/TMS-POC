import DeleteApplicationForm from '../../ui/applications/DeleteApplicationForm'
import { deleteApplicationRequest, hideDeleteApplicationForm } from '../../../actions'
import { connect } from 'react-redux'

const mapStateToProps = (state, props) => ({
    selectedEntries: state.application.selectedApplicationEntries,
    isDeletingApplication: state.application.deleteApplication.deletingApplication,
    hasError: state.application.deleteApplication.hasErrorOnDeletingApplication
})

const mapDispatchToProps = dispatch => ({
    onDeleteApplication(entriesToDelete) {
        console.log("on delete")
        dispatch(deleteApplicationRequest(entriesToDelete))
    },
    onCancel() {
        dispatch(hideDeleteApplicationForm())
    }
})

export default connect(mapStateToProps, mapDispatchToProps)(DeleteApplicationForm)