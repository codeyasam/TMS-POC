import EditApplicationForm from '../../ui/applications/EditApplicationForm'
import { editApplicationRequest } from '../../../actions/applicationActions'
import { connect } from 'react-redux'

const mapStateToProps = (state, props) => ({
    selectedApplication: state.application.selectedApplicationEntries[0],
    isEditingApplication: state.application.editApplication.editingApplication
})

const mapDispatchToProps = dispatch => ({
    onEditApplication(application) {
        dispatch(editApplicationRequest(application))
    }
})

export default connect(mapStateToProps, mapDispatchToProps)(EditApplicationForm)