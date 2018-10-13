import FormModal from '../../ui/generic/ModalForm'
import { hideDeleteApplicationForm, completeDeletingApplication } from '../../../actions'
import { connect } from 'react-redux'

const mapStateToProps = (state, props) => ({
    isVisible: state.application.deleteApplication.deleteApplicationFormVisibility,
    applicationList: state.application.applicationList
})

const mapDispatchToProps = dispatch => ({
    onCloseModal() {
        dispatch(hideDeleteApplicationForm())
        dispatch(completeDeletingApplication())
    }
})

export default connect(mapStateToProps, mapDispatchToProps)(FormModal)